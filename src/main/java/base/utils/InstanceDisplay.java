package base.utils;

import base.Main;
import base.core.GLIAAirlines;
import base.core.GLIAAirlinesNoConstraints;
import base.enums.Instance;
import org.apache.commons.cli.CommandLine;

import java.lang.reflect.Field;
import java.util.Arrays;

// Affichage des instances
public class InstanceDisplay {

    private final CommandLine line;

    public InstanceDisplay(CommandLine line) { this.line = line; }

    public void displayWithoutConstraints() {
        if (line.hasOption("instance")) displayInstanceResultWithoutConstraints();
        else displayAllInstancesWithoutConstraints();
    }

    public void displayWithConstraints() {
        // Affichage du string "Instance N°X |"
        try {
            Instance inst = Instance.valueOf(line.getOptionValue("instance"));
            Field field = inst.getClass().getField(inst.name());
            int index = Arrays.asList(inst.getClass().getFields()).indexOf(field);
            System.out.print("Instance N°" + String.valueOf(index + 1) + " | ");
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

        if (line.hasOption("instance")) displayInstanceResultWithConstraints();
        else displayAllInstancesWithConstraints();
    }

    private void displayInstanceResultWithConstraints() {
        (new GLIAAirlines()).solve(Instance.valueOf(line.getOptionValue("instance")), Main.timeout, Main.allSolutions);
    }

    private void displayAllInstancesWithConstraints() {
        for (Instance instance : Instance.values())
            (new GLIAAirlines()).solve(instance, Main.timeout, Main.allSolutions);
    }

    private void displayInstanceResultWithoutConstraints() {
        final int nb_dividers = Instance.valueOf(line.getOptionValue("instance")).nb_dividers;
        final int capacity = Instance.valueOf(line.getOptionValue("instance")).capacity;
        final int[] exits = Instance.valueOf(line.getOptionValue("instance")).exits;

        System.out.println("Résultat de l'instance N°"
                + line.getOptionValue("instance").charAt(4)
                + " sans Programmation par Contraintes :");

        GLIAAirlinesNoConstraints.dividers(nb_dividers, capacity, exits);
    }

    private void displayAllInstancesWithoutConstraints() {
        System.out.println("Affichage de toutes les instances sans programmation par contrainte :\n");
        int i = 0;
        for (Instance instance : Instance.values()) {
            System.out.println("Instance " + ++i + " :");
            GLIAAirlinesNoConstraints.dividers(instance.nb_dividers, instance.capacity, instance.exits);
            System.out.println("\n");
        }
    }
}