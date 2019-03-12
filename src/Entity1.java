public class Entity1 extends Entity
{

    // Perform any necessary initialization in the constructor
    public Entity1()
    {
        distanceTable[0][0] = 1;
        distanceTable[1][1] = 0;
        distanceTable[2][2] = 1;
        distanceTable[3][3] = 999; //mergat o klartkok123


        //printDT();

        /*Packet pk0 = new Packet(1,0,distanceTable[0]);
        Packet pk2 = new Packet(1,2,distanceTable[1]);*/


        /*NetworkSimulator.toLayer2(pk2);
        NetworkSimulator.toLayer2(pk0);*/
    }

    // Handle updates when a packet is received.  Students will need to call
    // NetworkSimulator.toLayer2() with new packets based upon what they
    // send to update.  Be careful to construct the source and destination of
    // the packet correctly.  Read the warning in NetworkSimulator.java for more
    // details.
    public void update(Packet p)
    {
        //System.out.println("Packet received from "+p.getSource() + "in "+p.getDest() +"at time "+ event0.getTime());
        System.out.println("-----UPDATE 1-----");
        /*for(int i=0; i<4; i++) {
            if (distanceTable[p.getDest()][p.getSource()] != p.getMincost(i)) {
                linkCostChangeHandler(i, p.getMincost(i));
                Packet pack = new Packet(1, i, distanceTable[1]);
                NetworkSimulator.toLayer2(pack);
            }
        }*/
        /*for(int i=0; i<4; i++){
            if(distanceTable[p.getSource()][p.getSource()]!=p.getMincost(p.getSource())){
                linkCostChangeHandler(i,p.getMincost(p.getSource()));
                Packet pack = new Packet(1,p.getSource(),distanceTable[p.getSource()]);
                NetworkSimulator.toLayer2(pack);
            }
        }*/
        // inkommande paket från närliggande entitet ex distanceTable[0][0] om ändrat, ändra i tabell
        if(distanceTable[p.getSource()][p.getSource()]!=p.getMincost(p.getDest())){
            System.out.println("distanceTable["+p.getSource()+"]["+p.getSource()+"] = "+p.getMincost(p.getDest()));
            distanceTable[p.getSource()][p.getSource()] = p.getMincost(p.getDest()); // ändrar distancetable till minsta kostnad från paket

            Packet pk0 = new Packet(1,p.getSource(),distanceTable[p.getSource()]);
            NetworkSimulator.toLayer2(pk0);
        }
        if(p.getSource()==2){
            p.getMincost(3);
        }
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
