public class Entity1 extends Entity
{

    // Perform any necessary initialization in the constructor
    int[] cost;
    public Entity1()
    {
        distanceTable[0][0] = 1;
        distanceTable[1][1] = 0;
        distanceTable[2][2] = 1;
        distanceTable[3][3] = 999; //mergat o klartkok123
        cost = new int[4];
        cost[0] = 1;
        cost[1] = 0;
        cost[2] = 1;
        cost[3] = 999;
    }

    // Handle updates when a packet is received.  Students will need to call
    // NetworkSimulator.toLayer2() with new packets based upon what they
    // send to update.  Be careful to construct the source and destination of
    // the packet correctly.  Read the warning in NetworkSimulator.java for more
    // details.
    public void update(Packet p)
    {
        System.out.println(p.getSource() + "HAR LANDAT");
        printDT();

    }

    public void linkCostChangeHandler(int whichLink, int newCost)
    {
        distanceTable[whichLink][whichLink] = newCost;
    }

    public void printDT()
    {
        System.out.println();
        System.out.println("         via");
        System.out.println(" D1 |   0   2");
        System.out.println("----+--------");
        for (int i = 0; i < NetworkSimulator.NUMENTITIES; i++)
        {
            if (i == 1)
            {
                continue;
            }

            System.out.print("   " + i + "|");
            for (int j = 0; j < NetworkSimulator.NUMENTITIES; j += 2)
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
