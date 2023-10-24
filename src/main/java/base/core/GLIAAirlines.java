package base.core;

import base.enums.Instance;
import org.chocosolver.solver.Model;
import org.chocosolver.solver.Solution;
import org.chocosolver.solver.Solver;
import org.chocosolver.solver.variables.IntVar;

import java.lang.reflect.Field;
import java.util.Arrays;

import static org.chocosolver.solver.search.strategy.Search.minDomLBSearch;
import static org.chocosolver.util.tools.ArrayUtils.append;

public class GLIAAirlines {

    IntVar[] dividers;
    Model model;

    public void solve(Instance inst, long timeout, boolean allSolutions) {

        // Création du modèle
        buildModel(inst);

        // Création du Solver
        Solver solver = model.getSolver();

        // Limitation temporelle de la recherche du Solver
        solver.limitTime(timeout);

        // Affichage de toutes les solutions
        try {
            Field field = inst.getClass().getField(inst.name());
            int index = Arrays.asList(inst.getClass().getFields()).indexOf(field);
            String id = field.getName().equals("instSujet") ? "base.enums.Instance du Sujet" : "base.enums.Instance N " + String.valueOf(index + 1);
            System.out.print(id + " | ");
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }


        if (allSolutions) {
            System.out.print("Solutions trouvées : \n");
            while (solver.solve()) {
                System.out.print("[ ");
                for (IntVar divider : dividers) {
                    System.out.print(divider.getValue() + " ");
                }
                System.out.print("]\n");
            }
        }

        // Affichage d'une unique solution
        else {
            Solution s = solver.findSolution();
            if (s != null) {
                System.out.print("Solution trouvée : ");
                for (IntVar divider : dividers) System.out.print(divider.getValue() + " ");
                System.out.println();
            }
        }
        model.getSolver().printStatistics();
    }

    public void buildModel(Instance inst) {

        // Nouvelle Instance du Modèle
        model = new Model("Aircraft Class Divider");

        // VARIABLES
        int n = inst.nb_dividers; // Nombre de séparateurs
        int m = inst.capacity; // Nombre de blocs
        int[] exits = inst.exits; // Tableau des sorties de secours
        // Tableau des distances entre les séparateurs
        IntVar[] diffs = model.intVarArray("d", (n * (n - 1)) / 2, 0, m, false);
        // Tableau spécifiant les séparateurs
        dividers = model.intVarArray("dividers", n, 0, m, false);

        // CONTRAINTES
        // Placement du premier et dernier séparateurs
        model.arithm(dividers[0], "=", 0).post();
        model.arithm(dividers[n - 1], "=", m).post();

        // Aucun séparateur n'est placé au bloc 1
        for (int i = 1; i < n; i++) model.arithm(dividers[i], ">", 1).post();

        // Aucun séparateur n'est placé au même endroit qu'une sortie
        for (int i = 0; i < inst.nb_exits(); i++)
            for (int j = 1; j < n - 1; j++)
                model.arithm(dividers[j], "!=", exits[i]).post();

        // Toutes distances entre les séparateurs doivent être unique
        for (int i = 0, k = 0 ; i < n - 1; i++) {
            // Les variables des séparateurs sont ordonnées
            model.arithm(dividers[i + 1], ">", dividers[i]).post();
            for (int j = i + 1; j < n; j++, k++) {
                // Déclaration de la contrainte de distance entre deux séparateurs distincts
                model.arithm(diffs[k], ">=", (j - i) * (j - i + 1) / 2).post();
                model.arithm(diffs[k], "<=", dividers[n - 1], "-", ((n - 1 - j + i) * (n - j + i)) / 2).post();
                model.scalar(new IntVar[]{dividers[j], dividers[i]}, new int[]{1, -1}, "=", diffs[k]).post();

            }
        }
        // Toutes les valeurs dans "diffs" doivent être différentes
        model.allDifferent(diffs).post();
    }

    public void configureSearch() {
        model.getSolver().setSearch(minDomLBSearch(append(dividers)));
    }

}