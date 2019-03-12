public class Entity0 extends Entity
{
    // Perform any necessary initialization in the constructor
    private Packet pkt;
    private Event event0;
    public Entity0()
    {
        event0 = new Event(0,1,0);
        /*distanceTable[0][0] = NetworkSimulator.cost[0][0];
        distanceTable[1][1] = NetworkSimulator.cost[0][1];
        distanceTable[2][2] = NetworkSimulator.cost[0][2];
        distanceTable[3][3] = NetworkSimulator.cost[0][3];*/
        distanceTable[0][0] = 0;
        distanceTable[1][1] = 1;
        distanceTable[2][2] = 3;
        distanceTable[3][3] = 7;
        printDT();
        //printDT();

        Packet pk1 = new Packet(0,1,distanceTable[1]);
        for (int i=0; i<4 ; i++){
            System.out.println("PK1 getMinCost("+i+")= "+pk1.getMincost(i));
        }
        /*Packet pk2 = new Packet(0,2,distanceTable[2]);
        Packet pk3 = new Packet(0,3,distanceTable[3]);*/


        NetworkSimulator.toLayer2(pk1);
        /*NetworkSimulator.toLayer2(pk2);
        NetworkSimulator.toLayer2(pk3);*/
    }

    // Handle updates when a packet is received.  Students will need to call
    // NetworkSimulator.toLayer2() with new packets based upon what they
    // send to update.  Be careful to construct the source and destination of
    // the packet correctly.  Read the warning in NetworkSimulator.java for more
    // details.
    public void update(Packet p)
    {
        System.out.println("Packet received from "+p.getSource() + " in "+p.getDest() +" at time "+ event0.getTime());
        System.out.println("-----UPDATE 0-----");

        /*for(int i=0; i<4; i++){
            if(distanceTable[p.getSource()][p.getSource()]!=p.getMincost(p.getSource())){
                linkCostChangeHandler(i,p.getMincost(p.getSource()));
                Packet pack = new Packet(0,p.getSource(),distanceTable[p.getSource()]);
                NetworkSimulator.toLayer2(pack);
            }
        }*/
        if(distanceTable[p.getSource()][p.getSource()]!=p.getMincost(p.getDest())){
            System.out.println("distanceTable["+p.getSource()+"]["+p.getSource()+"] = "+p.getMincost(p.getDest()));
            distanceTable[p.getSource()][p.getSource()] = p.getMincost(p.getDest());
        }
    }

    public void linkCostChangeHandler(int whichLink, int newCost)
    {
        distanceTable[0][whichLink] = newCost;
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
