public class GLIAAirlinesNoConstraints {

    public static void dividers(int n, int m, int[] exits) {

        int[] separators = new int[n];

        // Placement du premier et dernier séparateur
        separators[0] = 0;
        separators[n - 1] = m;

        // Début en position 2 pour avoir les deux blocs de la première classe
        if(placeSeparators(1, 2, separators, exits, m - 2))
            for (int separator : separators) System.out.print(separator + " ");

        else System.out.println("Pas de Solution !");
    }

    private static boolean placeSeparators(int index, int position, int[] separators, int[] exits, int m) {

        if (index == separators.length - 1) return true;

        for (int i = position; i < m; i++)
            if (isExitsValid(i, exits) && isDistanceUnique(i, separators, index)) {
                separators[index] = i;

                if (placeSeparators(index + 1, i + 1, separators, exits, m)) return true;
            }

        return false;
    }

    private static boolean isDistanceUnique(int position, int[] separators, int index) {

        for (int i = 0; i < index; i++)
            for (int j = i + 1; j < index; j++)
                if (separators[j] - separators[i] == position - separators[index - 1]
                        || separators[j] - separators[i] == separators[separators.length-1] - position)
                    return false;

        return true;
    }

    private static boolean isExitsValid(int idx, int[] exits){
        for (int e : exits) if (e == idx) return false;
        return true;
    }

}