package base.options;

import org.apache.commons.cli.CommandLine;

// Vérification des options spécifiques
public class OptionsChecker {

    private Long timeout;
    private Boolean allSolutions;

    public OptionsChecker(Long timeout, Boolean allSolutions) {
        this.timeout = timeout;
        this.allSolutions = allSolutions;
    }

    public void checkOption(CommandLine line, String option) {
        switch (option) {
            case "instance":
            case "with-constraint":
            case "default":
                break;
            case "timeout":
                this.timeout = Long.parseLong(line.getOptionValue(option));
                break;
            case "all":
                this.allSolutions = true;
                break;
            default:
                System.err.println("Bad parameter: " + option);
                System.exit(2);
        }
    }

    public Long getTimeout() { return this.timeout; }

    public Boolean getAllSolutions() { return this.allSolutions; }
}
