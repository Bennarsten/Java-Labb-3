public class Entity0 extends Entity
{
    // Perform any necessary initialization in the constructorsdds
    private Packet pkt;
    private Event event0;
    public Entity0()
    {
        event0 = new Event(0,1,0);

        int i, j;
        for(i = 0; i < 4; i++){
            for(j = 0; j < 4; j++){
                distanceTable[i][j] = 999;
            }
        }

        distanceTable[0][0] = 0;
        distanceTable[1][1] = 1;
        distanceTable[1][0] = 1;
        distanceTable[2][2] = 3;
        distanceTable[2][0] = 3;
        distanceTable[3][3] = 7;
        distanceTable[3][0] = 7;

        printDT();

        //MC = 0, 1, 3, 7 - min cost to entities
        int []mc = new int[4];
        for(i = 0; i < 4; i++){
            mc[i] = distanceTable[i][0];
        }

        Packet pk1 = new Packet(0,1,mc);
        Packet pk2 = new Packet(0,2,mc);
        Packet pk3 = new Packet(0,3,mc);

        NetworkSimulator.toLayer2(pk1);
        NetworkSimulator.toLayer2(pk2);
        NetworkSimulator.toLayer2(pk3);
    }

    int min0(int a, int b, int c)
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
    public void update(Packet p) {
        //System.out.println("rtpdate0 invoked at time %f \n", NetworkSimulator.time);
        if (p.getDest() != 0) {
            System.out.println("Wrong dest");
            return;
        }

        int flag = 0;
        int j = p.getSource();

        System.out.println("packet received in node 0 from node " + p.getSource());

        //om dT[till][via inkommet paket] större än dT[till inkommet paket][0]
        // dt[0-3][ex 1] större än dt[ex 1][0]
        for (int i = 0; i < 4; i++) {
            if (distanceTable[i][j] > distanceTable[j][0] + p.getMincost(i)) { //p.getMincost(i) ex: inkommet pk0
                distanceTable[i][j] = distanceTable[j][0] + p.getMincost(i); // 0, 1, 3, 7;

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
            for (int i = 1; i < 4; i++)
                mc[i] = min0(distanceTable[i][1], distanceTable[i][2], distanceTable[i][3]);
            mc[0] = 0;

            Packet pk1 = new Packet(0, 1, mc);
            Packet pk2 = new Packet(0, 2, mc);
            Packet pk3 = new Packet(0, 3, mc);

            NetworkSimulator.toLayer2(pk1);
            NetworkSimulator.toLayer2(pk2);
            NetworkSimulator.toLayer2(pk3);
        }
    }


        public void linkCostChangeHandler ( int whichLink, int newCost)
        {
            //distanceTable[0][whichLink] = newCost;
        }

        public void printDT ()
        {
            System.out.println();
            System.out.println("           via");
            System.out.println(" D0 |   1   2   3");
            System.out.println("----+------------");
            for (int i = 1; i < NetworkSimulator.NUMENTITIES; i++) {
                System.out.print("   " + i + "|");
                for (int j = 1; j < NetworkSimulator.NUMENTITIES; j++) {
                    if (distanceTable[i][j] < 10) {
                        System.out.print("   ");
                    } else if (distanceTable[i][j] < 100) {
                        System.out.print("  ");
                    } else {
                        System.out.print(" ");
                    }

                    System.out.print(distanceTable[i][j]);
                }
                System.out.println();
            }
        }
    }
