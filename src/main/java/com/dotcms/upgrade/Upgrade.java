package com.dotcms.upgrade;

import com.dotmarketing.startup.StartupTask;

import static java.lang.System.out;
public class Upgrade {

    public static void main(final String [] args) throws Exception  {

        if (args.length> 0) {

            final String taskClass = args[0];

            out.println("Running the upgrade task: " + taskClass);

            out.println("Doing Init the framework");
            InitService.getInstance().init();
            out.println("Done Init the framework");

            final StartupTask startupTask = (StartupTask)Class.forName(taskClass).newInstance();

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

                out.println( "The the upgrade task: " + taskClass + " not found");
            }
            System.exit(0);

        } else {

            out.println("Must include the upgrade class name before as a parameter");
            System.exit(1);
        }
    }
}
