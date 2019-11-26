package iprouterfunction;

import java.util.Scanner;

/**
 *
 * @author asvar
 */
public class IPRouterFunction {

    public static void main(String[] args) {
        
        // Initialize variables and input scanner
        int tableSize;
        String ip;
        String ipSplit[];
        Scanner s = new Scanner(System.in);

        // Get ip address
        System.out.print("Enter IP Address: ");
        ip = s.nextLine();
        
        // Divide ip address into octets
        ipSplit = ip.split("[.]");
        
        // Get number of table entries
        System.out.print("Enter Number of Routing Table Entries: ");
        tableSize = s.nextInt();
        
        // Gather table entries
        String sc;
        System.out.println();
        sc = s.nextLine();
        String rTable[] = new String[tableSize];
        System.out.println("Enter routing table contents in format Address/Mask Next Hop (ex. 132.45.23.0/33 Router 0): ");

        // Store routing table entries
        for (int i = 0; i < tableSize; i++) {
            rTable[i] = s.nextLine();
        }

        // Array for storing each value in each entry
        String rTableSplit[][] = new String[tableSize][];

        // Split items between periods and forward slashes
        for (int i = 0; i < tableSize; i++) {
            rTableSplit[i] = rTable[i].split("[./ ]");
        }

        // Insert blank line
        System.out.println();

        int i;
        // Iterate through entries in routing table
        for (i = 0; i < tableSize; i++) {
            
            // If there is no match by last iteration, print default case
            if (i == tableSize - 1) {
                System.out.println("IP Address is routed to: " + rTableSplit[i][rTableSplit[i].length - 2] + " " + rTableSplit[i][rTableSplit[i].length - 1]);
                break;
            }
            
            // Get number of network bits for current entry
            int mask = Integer.parseInt(rTableSplit[i][4]);
            
            
            // Initialize values of subnet mask to 0
            int networkMask[] = {0, 0, 0, 0};
            
            // Initialize iterations and current byte number
            int iteration = 7;
            int byteNum = 0;
            
            // Iterate through number of network bits 
            while (mask > 0) {
                if (iteration == 0) {
                    // On last iteration add 1 and move to next octet in subnet mask
                    networkMask[byteNum] += (int)Math.pow(2, iteration);
                    byteNum++;
                    
                    // Restart iteration
                    iteration = 7;
                } else {
                    // Add to current octet of subnet mask
                    networkMask[byteNum] += (int)Math.pow(2, iteration);
                    iteration--;
                }
                mask--;
            }
            int k = 0;
            
            // Iterate through each octet of ip address
            for (k = 0; k < ipSplit.length; k++) {
                
                // Perform binary AND on ip and network mask
                int binAnd = networkMask[k] & Integer.parseInt(ipSplit[k]);
                
                // Compare result of binary AND to IP Address in routing table entry
                if (binAnd != Integer.parseInt(rTableSplit[i][k])) {
                    // Break if no match
                    break;
                }
            }

            // If there is a match, print destination
            if (k == ipSplit.length) {
                System.out.println("IP Address is routed to: " + rTableSplit[i][rTableSplit[i].length - 2] + " " + rTableSplit[i][rTableSplit[i].length - 1]);
                break;
            }


        }
    }
}
