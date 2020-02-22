package com.aexiz.daviz.simulation.algorithm.wave;

import com.aexiz.daviz.frege.simulation.Process.TProcessDescription;
import com.aexiz.daviz.frege.simulation.Set.TSet;
import com.aexiz.daviz.frege.simulation.algorithm.wave.Tarry.TDUI;
import com.aexiz.daviz.simulation.*;
import com.aexiz.daviz.simulation.algorithm.information.*;
import com.aexiz.daviz.simulation.algorithm.wave.tarry.TarryAssumption;
import com.aexiz.daviz.simulation.algorithm.wave.tarry.TarryDecided;
import com.aexiz.daviz.simulation.algorithm.wave.tarry.TarryTerminated;
import com.aexiz.daviz.simulation.algorithm.wave.tarry.TarryToken;
import frege.prelude.PreludeBase.TTuple2;
import frege.prelude.PreludeBase.TTuple3;
import frege.run8.Thunk;

import java.util.List;

import static com.aexiz.daviz.frege.simulation.algorithm.wave.Tarry.procDesc;

public class Tarry extends AbstractFregeBasicAlgorithm {

    public Tarry() {
        assumption = TarryAssumption.makeAssumption();
    }

    @Override
    public MessageInformation makeAndUnloadMessage(FregeHelper helper, Object o) {
        FregeAlgorithm.validateParameters(helper, o);

        if ((Short) o == 0) return new TarryToken();
        throw new Error("Invalid Haskell unit");
    }

    @Override
    public StateInformation makeAndUnloadState(FregeHelper helper, Object o) {
        FregeAlgorithm.validateParameters(helper, o);
        abstract class TarryDUI implements PropertyVisitor {
        }
        class TarryState implements StateInformation {
            boolean hasToken;
            TarryDUI dui;
            List<Channel> neighbors;

            public String toString() {
                return "(" + hasToken + "," + dui + "," + neighbors + ")";
            }

            public void buildProperties(PropertyBuilder builder) {
                builder.simpleProperty("Has token?", String.valueOf(hasToken));
                builder.compoundProperty("State", dui);
                builder.compoundProperty("Neighbors", new PropertyVisitor() {
                    public void buildProperties(PropertyBuilder builder) {
                        builder.simpleProperty("", neighbors.size() + " elements");
                        for (int i = 0, size = neighbors.size(); i < size; i++) {
                            builder.simpleProperty(i + ":", neighbors.get(i).to.getLabel());
                        }
                    }
                });
            }
        }
        class TarryReceived extends TarryDUI {
            private Channel c;

            public String toString() {
                return "Received<" + c + ">";
            }

            public void buildProperties(PropertyBuilder builder) {
                builder.simpleProperty("", "Received");
                builder.simpleProperty("From:", c.to.getLabel());
            }
        }
        class TarryReplied extends TarryDUI {
            private Channel c;

            public String toString() {
                return "Replied<" + c + ">";
            }

            public void buildProperties(PropertyBuilder builder) {
                builder.simpleProperty("", "Replied");
                builder.simpleProperty("To:", c.to.getLabel());
            }
        }
        class TarryUndefined extends TarryDUI {
            public String toString() {
                return "Undefined";
            }

            public void buildProperties(PropertyBuilder builder) {
                builder.simpleProperty("", "Undefined");
            }
        }
        class TarryInitiator extends TarryDUI {
            public String toString() {
                return "Initiator";
            }

            public void buildProperties(PropertyBuilder builder) {
                builder.simpleProperty("", "Initiator");
            }
        }
        @SuppressWarnings("unchecked")
        TTuple3<Boolean, TDUI, TSet<TTuple2<Integer, Integer>>> st =
                (TTuple3<Boolean, TDUI, TSet<TTuple2<Integer, Integer>>>) o;
        TarryState result = new TarryState();
        result.hasToken = st.mem1.call();
        TDUI dui = st.mem2.call();
        if (dui.asReceived() != null) {
            TarryReceived r = new TarryReceived();
            r.c = helper.getChannelByTuple(dui.asReceived().mem1.call());
            result.dui = r;
        } else if (dui.asReplied() != null) {
            TarryReplied r = new TarryReplied();
            r.c = helper.getChannelByTuple(dui.asReplied().mem1.call());
            result.dui = r;
        } else if (dui.asUndefined() != null) {
            result.dui = new TarryUndefined();
        } else if (dui.asInitiator() != null) {
            result.dui = new TarryInitiator();
        } else {
            throw new Error("Invalid DUI value");
        }
        result.neighbors = helper.forEdgeSet(st.mem3.call());
        return result;
    }

    @Override
    public ResultInformation makeAndUnloadResult(FregeHelper helper, Object o) {
        FregeAlgorithm.validateParameters(helper, o);
        return (Boolean) o ? new TarryTerminated() : new TarryDecided();
    }

    @Override
    public TProcessDescription<Object, Object, Object, Object> getProcessDescription(FregeHelper helper) {
        return procDesc(Thunk.lazy(helper.getIdByNode(assumption.getInitiator()))).simsalabim();
    }

}
