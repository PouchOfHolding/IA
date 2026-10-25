import java.util.*;

class BestFirst {
    protected Queue<State> abertos;
    private Map<Ilayout, State> fechados;
    private State actual;
    private Ilayout objective;

    static class State {
        private Ilayout layout;
        private State father;
        private double g, h;

        public State(Ilayout l, State n) {
            layout = l;
            father = n;
            if (father != null)
                g = father.g + l.getK();
            else g = 0.0;
            h = l.getH();
        }

        public String toString() { return layout.toString(); }
        public double getG() { return g; }
        public double getF() { return g + h; }
        public int hashCode() { return toString().hashCode(); }

        public boolean equals(Object o) {
            if (o == null) return false;
            if (this.getClass() != o.getClass()) return false;
            State n = (State) o;
            return this.layout.equals(n.layout);
        }
    }

    final private List<State> sucessores(State n) {
        List<State> sucs = new ArrayList<>();
        List<Ilayout> children = n.layout.children();
        for (Ilayout e : children) {
            if (n.father == null || !e.equals(n.father.layout)) {
                State nn = new State(e, n);
                sucs.add(nn);
            }
        }
        return sucs;
    }

    final public Iterator<State> solve(Ilayout s, Ilayout goal) {
        this.objective = goal;
        abertos = new PriorityQueue<>(10, (s1, s2) -> {
            int c = Double.compare(s1.getF(), s2.getF());
            return c != 0 ? c : Double.compare(s2.getG(), s1.getG());
        });
        fechados = new HashMap<>();
        abertos.add(new State(s, null));
        List<State> sucs;
        
        while(!abertos.isEmpty()){  

            actual = abertos.poll();

            if( actual.layout.isGoal(objective)){

                List<State> caminho = new ArrayList<>();
                State temp = actual;
                while(temp != null){
                    caminho.add(temp);
                    temp = temp.father;
                }

                Collections.reverse(caminho);

                return caminho.iterator();
            }

            fechados.put(actual.layout, actual);
            sucs = sucessores(actual);

            for(State child : sucs){

                if(!fechados.containsKey(child.layout)){

                    State old = null;
                    for(State st : abertos)
                        if(st.equals(child)){ old = st; break; }

                    if(old == null){
                        abertos.add(child);
                    } else if(child.getG() < old.getG()){
                        abertos.remove(old);
                        abertos.add(child);
                    }
                }
            }
        }

        return null;
    }
}