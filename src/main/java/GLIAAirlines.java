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
        if(allSolutions)
            while(solver.solve()){
                System.out.print("Solutions trouvées : ");
                for (IntVar divider : dividers) System.out.print(divider.getValue() + " ");
            }
        // Affichage d'une unique solution
        else {
            Solution s = solver.findSolution();
            if(s != null) {
                System.out.print("Solution trouvée : ");
                for (IntVar divider : dividers) System.out.print(divider.getValue() + " ");
            }
        }
        
        model.getSolver().printStatistics();
    }

    public void buildModel(Instance inst) {
        // A new model instance
        model = new Model("Aircraft Class Divider ");

        // VARIABLES
        // here!

        // CONSTRAINTS
        // here!
    }

    public void configureSearch() {
        model.getSolver().setSearch(minDomLBSearch(append(dividers)));
    }

}