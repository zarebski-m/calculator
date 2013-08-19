/*
 * The MIT License
 *
 * Copyright 2013 Marcin Zarebski <zarebski.m[AT]gmail.com>.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package calculator.command;

import calculator.exception.command.CommandParseException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Command {
    public static enum CommandType {
        DefineFunction("func"),
        DefineConstant("const"),
        Unknown("");

        private final Pattern pattern;

        private CommandType(final String pattern) {
            this.pattern = Pattern.compile(pattern);
        }

        private boolean matches(final String command) {
            return pattern.matcher(command).matches();
        }

        public static CommandType getCommandType(final String command) {
            for (CommandType type : CommandType.values()) {
                if (type.matches(command)) {
                    return type;
                }
            }
            return CommandType.Unknown;
        }
    }

    private final CommandType type;

    private final String param;

    private final String content;

    public Command(final CommandType type, final String name, final String content) {
        this.type = type;
        this.param = name;
        this.content = content;
    }

    public CommandType getType() {
        return type;
    }

    public String getParam() {
        return param;
    }

    public String getContent() {
        return content;
    }

    @Override
    public String toString() {
        return new StringBuilder().append(type.toString()).append(" ").append(param).append(" ")
                .append(content).toString();
    }

    public static final class Builder {
        private static final Pattern commandPattern = Pattern.compile(
                ":(\\w+)\\s+([a-zA-Z][a-zA-Z0-9_]*)\\s*(.*)");

        private static final int INDEX_NAME = 1;

        private static final int INDEX_PARAM = 2;

        private static final int INDEX_CONTENT = 3;

        private CommandType type;

        private String param;

        private String content;

        public Builder withName(final String commandName) {
            this.type = CommandType.getCommandType(commandName);
            return this;
        }

        public Builder withParam(final String param) {
            this.param = param;
            return this;
        }

        public Builder withContent(final String content) {
            this.content = content;
            return this;
        }

        public Builder parse(final String line) throws CommandParseException {
            Matcher matcher = commandPattern.matcher(line);
            if (matcher.matches()) {
                this.type = CommandType.getCommandType(matcher.group(INDEX_NAME));
                this.param = matcher.group(INDEX_PARAM);
                this.content = matcher.group(INDEX_CONTENT);
            } else {
                throw new CommandParseException(line);
            }
            return this;
        }

        public Command build() {
            return new Command(type, param, content);
        }
    }
}
