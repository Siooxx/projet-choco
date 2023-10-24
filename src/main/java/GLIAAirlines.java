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
        IntVar[] diffs = new IntVar[n - 1];
        dividers = model.intVarArray("dividers", n, 0, m, false);

        // Contraintes
        model.arithm(dividers[0], "=", 0).post();

        for (int i = 1; i < n; i++) model.arithm(dividers[i], ">", 1).post();

        model.arithm(dividers[n - 1], "=", m).post();

        for (int i = 0; i < inst.nb_exits(); i++)
            for (int j = 0; j < n; j++)
                model.arithm(dividers[j], "!=", exits[i]).post();

        for (int i = 0; i < n - 1; i++) {
            diffs[i] = model.intVar("diff_" + i, 1, m, false);
            model.arithm(diffs[i], "=", dividers[i + 1], "-", dividers[i]).post();
        }
        model.allDifferent(diffs).post();

    }

    public void configureSearch() {
        model.getSolver().setSearch(minDomLBSearch(append(dividers)));
    }

}