package com.vite.cli;

import java.util.Objects;
import java.util.function.Function;

/**
 * Holds information needed to create a command
 */
class Command {
    private final CommandName name;
    private final String description;
    private final Function<String [], String> function;

    Command (CommandName name, String description, Function<String[], String> function) {
        this.name = name;
        this.description = description;
        this.function = function;
    }

    /**
     * Returns the name of this command
     *
     * @return the name of this command
     */
    CommandName getName() {
        return name;
    }

    /**
     * Returns the description of this command
     *
     * @return String description
     */
    String getDescription() {
        return description;
    }

    /**
     * Executes the command
     *
     * @return String result of the command
     */
    String execute(String ... args) {
        return function.apply(args);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, description, function);
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Command)) {
            return false;
        }

        Command command = (Command) o;

        return Objects.equals(command.description, this.description) &&
                Objects.equals(command.name, this.name) &&
                Objects.equals(command.function, this.function);
    }

    @Override
    public String toString() {
        return "Command{" +
                "name=" + name +
                ", description='" + description + '\'' +
                ", function=" + function +
                '}';
    }
}
