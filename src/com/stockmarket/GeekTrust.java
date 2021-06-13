package com.stockmarket;

import com.stockmarket.orchestrator.TradeOrchestrator;
import com.stockmarket.logger.LogLevel;
import com.stockmarket.logger.Logger;
import com.stockmarket.orderexecutor.IOrderExecutor;
import com.stockmarket.orderexecutor.OrderExecutor;
import com.stockmarket.parser.IOrderParser;
import com.stockmarket.parser.OrderParser;
import com.stockmarket.transaction.IHistoryCollector;
import com.stockmarket.transaction.TransactionHistoryCollector;

import java.io.FileNotFoundException;
import java.util.Scanner;

public class GeekTrust {

    private static final Logger log = Logger.getLogger("GeekTrust");
    private static boolean runSingleFile = true;

    public static void main(String[] args) {

        if (args == null || args.length <= 0 || args[0] == null) {
            log.error("No arguments passed");
            System.out.println("Invalid/No arguments passed");
            System.out.println("run \"java GeekTrust <filepath> <optional_loglevel>\" ; ensure file is present and has valid orders");
            System.out.println("e.g. \"java GeekTrust a.txt\" or \"java GeekTrust a.txt 1\" ");
            System.out.println("Log levels - 1. debug, 2. info 3. error; default - none");

            return;
        }
        setLogLevel(args);

        String filePath = args[0];

        if (runSingleFile) {
            execute(filePath);
        } else {
            multiFile(filePath);
        }

    }

    public static void execute(String filePath) {
        IOrderParser parser = OrderParser.getInstance();
        IOrderExecutor executor = new OrderExecutor();
        try {
            log.info("Reading file at path " + filePath);
            parser.initialiseFileReader(filePath);
        } catch (FileNotFoundException e) {
            log.error("File not found");
            System.out.println(e.getMessage());
            System.out.println("File not found at " + filePath );
            System.out.println("run \"java GeekTrust <filepath> <optional_loglevel>\" ; ensure file is present and has valid orders");
            System.out.println("e.g. \"java GeekTrust a.txt\" or \"java GeekTrust a.txt 1\" ");
            System.out.println("Log levels - 1. debug, 2. info 3. error; default - none");

            return;
        }
        log.info("File loaded successfully");
        log.info("Initiating trade facilitator");

        IHistoryCollector historyCollector = TransactionHistoryCollector.getInstance();
        TradeOrchestrator trader = new TradeOrchestrator(executor, parser,historyCollector);
        trader.trade();
    }

    private static void multiFile(String filePath) {
        while (true) {
            if (filePath == null || filePath.isEmpty()) {
                System.out.println("Wrong path entered, Enter correct file path ");
                System.out.println("Exiting.");
                break;
            }
            execute(filePath);
            System.out.println("Enter 1 for next file, 0 for exit");
            Scanner scan = new Scanner(System.in);
            int operation = scan.nextInt();
            scan.nextLine();
            if (operation != 1) {
                System.out.println("Exiting.");
                break;
            } else {
                System.out.println("Enter new file path ");
                filePath = scan.nextLine();
            }

        }
    }

    /**
     * sets log level
     *
     * @param args
     */
    private static void setLogLevel(String[] args) {
        Integer num = 0;
        if (args.length >= 2) {
            try {
                num = Integer.parseInt(args[1]);
            } catch (Exception e) {
                num = 0;
            }
        }
        Logger.setLogLevel(LogLevel.fromInteger(num));
    }
}

