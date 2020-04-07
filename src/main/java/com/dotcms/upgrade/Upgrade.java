package com.dotcms.upgrade;

import static java.lang.System.out;
import java.util.regex.Pattern;
import com.dotmarketing.startup.StartupTask;

public class Upgrade {

    Pattern num = Pattern.compile("^[0-9]{4,6}$");

    int isArgANumber(String input) {
        if (num.matcher(input).find()) {
            return Integer.parseInt(num.matcher(input).group(0));
        }
        return -1;
    }

    StartupTask getClazz(String input) throws InstantiationException, IllegalAccessException, ClassNotFoundException {
        int task = isArgANumber(input);
        return (StartupTask) Class.forName(input).newInstance();
    }

    public static void main(final String[] args) throws Exception {

        run("com.dotmarketing.startup.runonce.Task04230FixVanityURLInconsistencies");
    }

    public static void run(final String... args) throws Exception {

        out.println("Doing Init the framework");
        InitService.init();
        out.println("Done Init the framework");

        TaskUtil tasks = new TaskUtil();
        try {
            System.out.println("Currently at db version:" + tasks.getDBVersion());
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        if (args.length > 0) {

            final String taskClass = args[0];

            out.println("Running the upgrade task: " + taskClass);

            final StartupTask startupTask = (StartupTask) Class.forName(taskClass).newInstance();

            out.println("Running the task: " + startupTask.getClass().getName());
            if (null != startupTask) {

                if (startupTask.forceRun()) {

                    out.println("Running the executeUpgrade: " + startupTask.getClass().getName());

                    try {
                        startupTask.executeUpgrade();
                    } catch (Exception e) {

                        out.println("Error: " + e.getMessage());
                        e.printStackTrace(out);
                    }

                    out.println("Ran the upgrade task: " + taskClass);

                } else {

                    out.println("The the upgrade task: " + taskClass + " does not need to be execute");
                }
            } else {

                out.println("The the upgrade task: " + taskClass + " not found");
            }

        } else {

            out.println("Must include the upgrade class name or the numeric upgrade id as a parameter");
        }
    }
}
