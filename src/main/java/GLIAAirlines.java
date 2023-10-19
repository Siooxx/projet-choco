import org.chocosolver.solver.Model;
import org.chocosolver.solver.variables.IntVar;

import static org.chocosolver.solver.search.strategy.Search.minDomLBSearch;
import static org.chocosolver.util.tools.ArrayUtils.append;

public class GLIAAirlines {

    IntVar[] dividers;
    Model model;

    public void solve(Instance inst, long timeout, boolean allSolutions) {

        buildModel(inst);
        model.getSolver().limitTime(timeout);

        StringBuilder st = new StringBuilder(
                String.format(model.getName() + "-- %s\n", inst.nb_dividers, " X ", inst.capacity));

        //solver call!

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