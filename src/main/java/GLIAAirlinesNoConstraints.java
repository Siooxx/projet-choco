import java.util.Arrays;

public class GLIAAirlinesNoConstraints {

    public static void dividers(int n, int m, int[] exits) {

        int[] separators = new int[n];

        // Placement du premier et dernier séparateur
        separators[0] = 0;
        separators[n - 1] = m;

        // Début en position 2 pour avoir les deux blocs de la première classe
        if(placeSeparators(1, 2, separators, exits, m))
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