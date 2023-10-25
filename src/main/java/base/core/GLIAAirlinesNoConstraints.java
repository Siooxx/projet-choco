package base.core;

import base.Main;
import java.util.Arrays;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

public class GLIAAirlinesNoConstraints {

    public static void dividers(int n, int m, int[] exits) {

        // Tableau des emplacements des séparateurs
        int[] separators = new int[n];
        // Initialisation d'un timer
        final AtomicBoolean timeoutReached = new AtomicBoolean(false);

        // Création d'un ScheduledExecutorService
        ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);

        // Mettre en place une tâche pour être exécutée après le timeout défini
        executor.schedule(new Runnable() {
            @Override
            public void run() { timeoutReached.set(true);}
        }, Main.timeout, TimeUnit.MILLISECONDS);

        // Placement du premier et dernier séparateur
        separators[0] = 0;
        separators[n - 1] = m;

        // Début en position 2 pour avoir les deux blocs de la première classe
        if(!timeoutReached.get() && placeSeparators(1, 2, separators, exits, m))
            System.out.print(Arrays.toString(separators));

        else System.out.println("Pas de Solution !");
    }

    private static boolean placeSeparators(int idxSeparator, int positionTabBlocs, int[] separators, int[] exits, int nbBlocs) {

        if (idxSeparator == separators.length - 1) return true;

        for (int i = positionTabBlocs; i < nbBlocs; i++)
            if (isExitsValid(i, exits) && isDistanceUnique(i, separators, idxSeparator)) {
                separators[idxSeparator] = i;

                if (placeSeparators(idxSeparator + 1, i + 1, separators, exits, nbBlocs)) return true;
            }

        return false;
    }

    private static boolean isDistanceUnique(int positionTabBlocs, int[] separators, int idxSeparator) {

        for (int i = 0; i < idxSeparator; i++)
            for (int j = i + 1; j < idxSeparator; j++)
                for (int k = 0; k <= idxSeparator; k++)
                    if (separators[j] - separators[i] == positionTabBlocs - separators[k]
                            || separators[j] - separators[i] == separators[separators.length-1] - positionTabBlocs)
                        return false;

        return true;
    }

    private static boolean isExitsValid(int idx, int[] exits){
        for (int e : exits) if (e == idx) return false;
        return true;
    }

}