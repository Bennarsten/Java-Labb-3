public class Entity2 extends Entity
{
    // Perform any necessary initialization in the constructor
    public Entity2()
    {
        int i, j;
        for(i = 0; i < 4; i++){
            for(j = 0; j < 4; j++){
                distanceTable[i][j] = 999;
            }
        }

        distanceTable[0][0] = 3;
        distanceTable[0][2] = 3;
        distanceTable[1][1] = 1;
        distanceTable[1][2] = 1;
        distanceTable[2][2] = 0;
        distanceTable[3][3] = 2;
        distanceTable[3][2] = 2;


        printDT();

        //MC = 0, 1, 3, 7 - min cost to entities
        int []mc = new int[4];
        for(i = 0; i < 4; i++){
            mc[i] = distanceTable[i][2];
        }

        Packet pk0 = new Packet(2,0,mc);
        Packet pk1 = new Packet(2,1,mc);
        Packet pk3 = new Packet(2,3,mc);

        NetworkSimulator.toLayer2(pk0);
        NetworkSimulator.toLayer2(pk1);
        NetworkSimulator.toLayer2(pk3);
    }

    //min0(distanceTable[0][1], distanceTable[0][2], distanceTable[0][3]);
    //                   == 2               == 3                == >3
    int min0(int a, int b, int c)
    {
        System.out.println("int A = "+a+"\tint b = "+b+"\tint c = "+c);
        int temp = (a <= b ? a : b);
        temp = (temp <= c ? temp : c);
        return temp;
    }

    // Handle updates when a packet is received.  Students will need to call
    // NetworkSimulator.toLayer2() with new packets based upon what they
    // send to update.  Be careful to construct the source and destination of
    // the packet correctly.  Read the warning in NetworkSimulator.java for more
    // details.
    public void update(Packet p)
    {
        //System.out.println("rtpdate0 invoked at time %f \n", NetworkSimulator.time);
        if (p.getDest() != 2) {
            System.out.println("Wrong dest");
            return;
        }

        int flag = 0;
        int j = p.getSource();

        System.out.println("packet received in node 0 from node " + p.getSource());

        //om dT[till][via inkommet paket] större än dT[till inkommet paket][0]
        // dt[0-3][ex 1] större än dt[ex 1][0]
        for (int i = 0; i < 4; i++) {
            if (distanceTable[i][j] > distanceTable[j][2] + p.getMincost(i)) { //p.getMincost(i) ex: inkommet pk0
                distanceTable[i][j] = distanceTable[j][2] + p.getMincost(i); // 0, 1, 3, 7;
                //distanceTable[0][3] = distanceTable[3][2] + p.getMincost(0)

                flag = 1;

            }
        }
        //change detected
        if (flag == 1) {
            System.out.println("Distance table in entity 0 updated: ");
            printDT();

            int[] mc = new int[4];
            for (int i = 0; i < 4; i++) {
                mc[i] = distanceTable[i][0];
            }
            for (int i = 0; i < 4; i++)
                mc[i] = min0(distanceTable[i][1], distanceTable[i][2], distanceTable[i][3]);
            mc[2] = 0;
            //distanceTable[0][1]

            Packet pk0 = new Packet(2, 0, mc);
            Packet pk1 = new Packet(2, 1, mc);
            Packet pk3 = new Packet(2, 3, mc);

            NetworkSimulator.toLayer2(pk0);
            NetworkSimulator.toLayer2(pk1);
            NetworkSimulator.toLayer2(pk3);
        }
    }

    public void linkCostChangeHandler(int whichLink, int newCost)
    {

    }

    public void printDT()
    {
        System.out.println();
        System.out.println("           via");
        System.out.println(" D2 |   0   1   3");
        System.out.println("----+------------");
        for (int i = 0; i < NetworkSimulator.NUMENTITIES; i++)
        {
            if (i == 2)
            {
                continue;
            }

            System.out.print("   " + i + "|");
            for (int j = 0; j < NetworkSimulator.NUMENTITIES; j++)
            {
                if (j == 2)
                {
                    continue;
                }

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
