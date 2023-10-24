package base.options;

import org.apache.commons.cli.Options;
import org.apache.commons.cli.Option;

// Création et configuration des options
public class OptionsConfig {

    public static Options configParameters() {
        final Options options = new Options();

        options.addOption(createOption("h", "help", "Display help message"));
        options.addOption(createOptionWithArg("i", "instance", "aircraft instance (#dividers, capacity, exit doors) - from inst1 to inst9"));
        options.addOption(createOption("a", "all", "all solutions"));
        options.addOption(createOptionWithArg("t", "timeout", "Set the timeout limit to the specified time"));
        options.addOption(createOption("c", "with-constraint", "Run the instance with the choco constraint model"));
        options.addOption(createOption("d", "default", "Run all the instance without the constraint model"));

        return options;
    }

    private static Option createOption(String shortOpt, String longOpt, String description) {
        return Option.builder(shortOpt).longOpt(longOpt).desc(description).build();
    }

    private static Option createOptionWithArg(String shortOpt, String longOpt, String description) {
        return Option.builder(shortOpt).longOpt(longOpt).desc(description).hasArg(true).build();
    }
}
