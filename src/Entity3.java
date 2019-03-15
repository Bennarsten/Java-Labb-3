public class Entity3 extends Entity
{
    // Perform any necessary initialization in the constructor
    public Entity3()
    {
        int i, j;
        for(i = 0; i < 4; i++){
            for(j = 0; j < 4; j++){
                distanceTable[i][j] = 999;
            }
        }

        distanceTable[0][0] = 7;
        distanceTable[0][3] = 7;
        distanceTable[2][2] = 2;
        distanceTable[2][3] = 2;
        distanceTable[3][3] = 0;

        printDT();

        //MC = 0, 1, 3, 7 - min cost to entities
        int []mc = new int[4];
        for(i = 0; i < 4; i++){
            mc[i] = distanceTable[i][1];
        }

        Packet pk0 = new Packet(3,0,mc);
        Packet pk1 = new Packet(3,1,mc);
        Packet pk2 = new Packet(3,2,mc);

        NetworkSimulator.toLayer2(pk0);
        NetworkSimulator.toLayer2(pk1);
        NetworkSimulator.toLayer2(pk2);

    }
    int min1(int a, int b, int c)
    {
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
        if (p.getDest() != 3) {
            System.out.println("Wrong dest");
            return;
        }

        int flag = 0;
        int j = p.getSource();

        System.out.println("packet received in node 0 from node " + p.getSource());

        //om dT[till][via inkommet paket] större än dT[till inkommet paket][0]
        // dt[0-3][ex 1] större än dt[ex 1][0]
        for (int i = 0; i < 4; i++) {
            if (distanceTable[i][j] > distanceTable[j][3] + p.getMincost(i)) { //p.getMincost(i) ex: inkommet pk0
                distanceTable[i][j] = distanceTable[j][3] + p.getMincost(i); // 0, 1, 3, 7;
                //distanceTable[0][2] = distanceTable[2][3] + p.getMincost(0);
                flag = 1;

            }
        }
        //change detected
        if (flag == 1) {
            System.out.println("Distance table in entity 1 updated: ");
            printDT();

            int[] mc = new int[4];
            for (int i = 0; i < 4; i++) {
                mc[i] = distanceTable[i][1];
            }
            for (int i = 0; i < 4; i++)
                mc[i] = min1(distanceTable[i][1], distanceTable[i][2], distanceTable[i][3]);
            mc[3] = 0;

            Packet pk0 = new Packet(3, 0, mc);
            Packet pk1 = new Packet(3, 1, mc);
            Packet pk2 = new Packet(3, 2, mc);

            NetworkSimulator.toLayer2(pk0);
            NetworkSimulator.toLayer2(pk1);
            NetworkSimulator.toLayer2(pk2);

        }

    }

    public void linkCostChangeHandler(int whichLink, int newCost)
    {

    }

    public void printDT()
    {
        System.out.println("         via");
        System.out.println(" D3 |   0   2");
        System.out.println("----+--------");
        for (int i = 0; i < NetworkSimulator.NUMENTITIES; i++)
        {
            if (i == 3)
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
