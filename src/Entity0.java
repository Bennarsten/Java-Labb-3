public class Entity0 extends Entity
{
    // Perform any necessary initialization in the constructor
    private Packet pkt;
    private Event event;
    private int[] cost;
    public Entity0()
    {
        //event0 = new Event();
        cost = new int[4];
        distanceTable[0][0] = 0;
        distanceTable[1][1] = 1;
        distanceTable[2][2] = 3;
        distanceTable[3][3] = 7;


        cost[0] = 0;
        cost[1] = 1;
        cost[2] = 3;
        cost[3] = 7;



        Packet pk1 = new Packet(0,1,cost);
        Packet pk2 = new Packet(0,2,cost);
        Packet pk3 = new Packet(0,3,cost);


        NetworkSimulator.toLayer2(pk1);
        NetworkSimulator.toLayer2(pk2);
        NetworkSimulator.toLayer2(pk3);


        /*for(int i=1; i<4; i++) {
            pkt = new Packet(0, i, cost);
            NetworkSimulator.toLayer2(pkt);


        }*/

    }

    // Handle updates when a packet is received.  Students will need to call
    // NetworkSimulator.toLayer2() with new packets based upon what they
    // send to update.  Be careful to construct the source and destination of
    // the packet correctly.  Read the warning in NetworkSimulator.java for more
    // details.
    public void update(Packet p)
    {

        printDT();
        pkt = new Packet(0,p.getSource(),cost);
        NetworkSimulator.toLayer2(pkt);

        for(int i=0; i<4; i++){
            if(distanceTable[i][i]!=p.getMincost(i)){
                linkCostChangeHandler(i,p.getMincost(i));
                Packet pack = new Packet(0,i,cost);
                NetworkSimulator.toLayer2(pack);
            }

        }
    }

    public void linkCostChangeHandler(int whichLink, int newCost)
    {
        distanceTable[whichLink][whichLink] = newCost;
        cost[whichLink] = newCost;
    }

    public void printDT()
    {
        System.out.println();
        System.out.println("           via");
        System.out.println(" D0 |   1   2   3");
        System.out.println("----+------------");
        for (int i = 1; i < NetworkSimulator.NUMENTITIES; i++)
        {
            System.out.print("   " + i + "|");
            for (int j = 1; j < NetworkSimulator.NUMENTITIES; j++)
            {
                if (distanceTable[i][j] < 10)
                {
                    System.out.print("   ");
                }
                else if (distanceTable[i][j] < 100)
                {
                    System.out.print("  ");
                }
                else
                {
                    System.out.print(" ");
                }

                System.out.print(distanceTable[i][j]);
            }
            System.out.println();
        }
    }
}
