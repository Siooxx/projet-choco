import org.chocosolver.solver.Model;
import org.chocosolver.solver.Solution;
import org.chocosolver.solver.Solver;
import org.chocosolver.solver.variables.IntVar;

import static org.chocosolver.solver.search.strategy.Search.minDomLBSearch;
import static org.chocosolver.util.tools.ArrayUtils.append;

public class GLIAAirlines {

    IntVar[] dividers;
    Model model;

    public void solve(Instance inst, long timeout, boolean allSolutions) {

        // Création du modèle
        buildModel(inst);

        //Création du Solver
        Solver solver = model.getSolver();

        // Limitation temporelle de la recherche du Solver
        solver.limitTime(timeout);

        // Affichage de toutes les solutions
        if (allSolutions){
            System.out.print("Solutions trouvées : \n");
            while (solver.solve()) {
                System.out.print("[");
                for (IntVar divider : dividers){
                    System.out.print(divider.getValue() + ", ");
                }
                System.out.print("]\n");
            }
        }

        // Affichage d'une unique solution
        else{
            Solution s = solver.findSolution();
            if (s != null) {
                System.out.print("Solution trouvée : ");
                for (IntVar divider : dividers) System.out.print(divider.getValue() + " ");
            }
        }

        model.getSolver().printStatistics();
    }

    public void buildModel(Instance inst) {

        // A new model instance
        model = new Model("Aircraft Class Divider");

        // Variable
        int n = inst.nb_dividers;
        int m = inst.capacity;
        int[] exits = inst.exits;
        IntVar[] diffs = model.intVarArray("d", (n * (n - 1)) / 2, 0, m, false);
        dividers = model.intVarArray("dividers", n, 0, m, false);

        // Contraintes
        model.arithm(dividers[0], "=", 0).post();

        for (int i = 1; i < n; i++) model.arithm(dividers[i], ">", 1).post();

        model.arithm(dividers[n - 1], "=", m).post();

        for (int i = 0; i < inst.nb_exits(); i++)
            for (int j = 1; j < n-1; j++)
                model.arithm(dividers[j], "!=", exits[i]).post();

        for (int i = 0, k = 0 ; i < n - 1; i++) {
            // // the mark variables are ordered
            model.arithm(dividers[i + 1], ">", dividers[i]).post();
            for (int j = i + 1; j < n; j++, k++) {
                // declare the distance constraint between two distinct marks
                // redundant constraints on bounds of diffs[k]
                model.arithm(diffs[k], ">=", (j - i) * (j - i + 1) / 2).post();
                model.arithm(diffs[k], "<=", dividers[n - 1], "-", ((n - 1 - j + i) * (n - j + i)) / 2).post();
                model.scalar(new IntVar[]{dividers[j], dividers[i]}, new int[]{1, -1}, "=", diffs[k]).post();

            }
        }

        model.allDifferent(diffs).post();

    }

    public void configureSearch() {
        model.getSolver().setSearch(minDomLBSearch(append(dividers)));
    }

}