class Client {
    final static double[] DIST1 = {0.9, 0.6};
    final static double[] DIST2 = {0.9, 0.8};
    final static double[] DIST3 = {0.55, 0.45};
    final static double[] DIST11 = {0.9, 0.6, 0.6, 0.6, 0.6, 0.6, 0.6, 0.6, 0.6, 0.6};
    final static double[] DIST12 = {0.9, 0.8, 0.8, 0.8, 0.7, 0.7, 0.7, 0.6, 0.6, 0.6};
    final static double[] DIST13 = {0.9, 0.8, 0.8, 0.8, 0.8, 0.8, 0.8, 0.8, 0.8, 0.8};
    final static double[] DIST14 = {0.55, 0.45, 0.45, 0.45, 0.45, 0.45, 0.45, 0.45, 0.45, 0.45};
    final static int NUM_RUNS = 1000000;
    
    public static void main(String [] args) {
        
        int[] playCounter = new int[10];
        int[] rewardCounter = new int[10];
        int plays = 0;
        
        double[] currentDistribution = DIST14;
        
        //initalizing
        for(int i = 0; i < currentDistribution.length; i++) {
            if(Math.random() < currentDistribution[i]) {
                rewardCounter[i]++;
            }
            playCounter[i]++;
            plays++;
        }
        
        for(int i = 0; i < NUM_RUNS; i++) {
            int j = ucb1(playCounter, rewardCounter, plays, currentDistribution.length);
            if(Math.random() < currentDistribution[j]) {
                rewardCounter[j]++;
            }
            playCounter[j]++;
            plays++;
            i++;
            if(i==1 || i==3 || i==6 || i==9 || i==10 || i==30|| i==60 || i==90 || i==100 || i==300 || i==600 || i==900 || i==1000 || i==3000 || i==6000 || i==9000 || i==10000 || i==30000 || i==60000 || i==90000 || i==100000 || i== 300000 || i==600000 || i==900000 || i==1000000) {
                printResults(i, playCounter, rewardCounter, currentDistribution, plays, true);
            }
            i--;
        }
        
        playCounter = new int[10];
        rewardCounter = new int[10];
        plays = 0;
        
        /*//initalizing
        for(int i = 0; i < currentDistribution.length; i++) {
            if(Math.random() < currentDistribution[i]) {
                rewardCounter[i]++;
            }
            playCounter[i]++;
            plays++;
        }*/
        
        double c = Double.parseDouble(args[0]);
        double d = currentDistribution[0] - currentDistribution[1];
        System.out.println("ENGREEDY: C = " + c + "\n");
        
        for(int i = 0; i < NUM_RUNS; i++) {
            int j = enGreedy(playCounter, rewardCounter, plays, currentDistribution.length, c, d);
            if(Math.random() < currentDistribution[j]) {
                rewardCounter[j]++;
            }
            playCounter[j]++;
            plays++;
            i++;
            if(i==1 || i==3 || i==6 || i==9 || i==10 || i==30|| i==60 || i==90 || i==100 || i==300 || i==600 || i==900 || i==1000 || i==3000 || i==6000 || i==9000 || i==10000 || i==30000 || i==60000 || i==90000 || i==100000 || i== 300000 || i==600000 || i==900000 || i==1000000) {
                printResults(i, playCounter, rewardCounter, currentDistribution, plays, false);
            }
            i--;
        }
    }
    
    //this returns the machine j according to the ucb1 policy.
    static int ucb1(int[] playCounter, int[] rewardCounter, int plays, int size) {
        double[] xBarJ = new double[size];
        for(int i = 0; i < size; i++) {
            xBarJ[i] = (double)rewardCounter[i] / playCounter[i];
        }
        double max = xBarJ[0] + Math.sqrt((2.0 * Math.log(plays)) / playCounter[0]);
        int j = 0;
        for(int i = 1; i < size; i++) {
            double test = xBarJ[i] + Math.sqrt((2.0 * Math.log(plays)) / playCounter[i]);
            if(test > max) {
                max = test;
                j = i;
            }
        }
        return j;
    }
    
    //this returns the machine j according the enGreedy policy
    static int enGreedy(int[] playCounter, int[] rewardCounter, int plays, int size, double c, double d) {
        double en = (c * size) / (d * d * plays);
        if(en > 1) en = 1.0;
        double max = (double)rewardCounter[0] / playCounter[0];
        int j = 0;
        for(int i = 1; i < size; i++) {
            double test = (double)rewardCounter[i] / playCounter[i];
            if(test > max) {
                max = test;
                j = i;
            }
        }
        if(Math.random() < en)
            return (int) (Math.random() * size);
        return j;
    }
    
    static void printResults(int j, int[] playCounter, int[] rewardCounter, double[] currentDistribution, int plays, boolean ucb) {
        System.out.println("i = " + j + ":");
        if(ucb) System.out.print("Play distribution for ucb1: ");
        else System.out.print("Play distribution for enGreedy: ");
        for(int i = 0; i < currentDistribution.length; i++) {
            System.out.print(i + ": " + playCounter[i] + "  ");
        }
        System.out.println();
        
        if(ucb) System.out.print("Reward distribution for ucb1: ");
        else System.out.print("Play distribution for enGreedy: ");
        for(int i = 0; i < currentDistribution.length; i++) {
            System.out.print(i + ": " + rewardCounter[i] + "  ");
        }
        System.out.println();
        
        int sum = 0;
        for(int i = 0; i < currentDistribution.length; i++) {
            sum += currentDistribution[i] * playCounter[i];
        }
        if(ucb) System.out.print("Regret for ucb1: ");
        else System.out.print("Regret for enGreedy: ");
        System.out.println(currentDistribution[0] * (j+1) - sum);
        if(ucb) System.out.println("% best machine played for ucb1: " + ((double)playCounter[0])/plays);
        else System.out.println("% best machine played for enGreedy: " + ((double)playCounter[0])/plays);
        System.out.println();
    }
}