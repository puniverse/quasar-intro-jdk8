package foo;

import co.paralleluniverse.fibers.Fiber;
import co.paralleluniverse.strands.Strand;
import co.paralleluniverse.strands.channels.Channel;
import co.paralleluniverse.strands.channels.Channels;

public class Main {
    public static void main(String[] args) throws Exception {
        final Channel<Integer> c = Channels.newChannel(0);

        new Fiber<Void>(() -> {
                for (int i = 0; i < 10; i++) {
                    c.send(i);
                    Strand.sleep(500);
                }
                c.close();
            }).start();
        
        Integer x;
        while((x = c.receive()) != null)
            System.out.println("=> " + x);
    }
}
