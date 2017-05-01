package com.kedialabs.batchingplant;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Map;

import com.google.common.collect.ImmutableMap;

import lombok.Getter;

@Getter
public enum ConcreteMixture {
    SLURRY("SLURRY", ImmutableMap.<RawMaterialType,Double>builder().put(RawMaterialType.CEMENT, 320d).put(RawMaterialType.FLYASH, 0d).put(RawMaterialType.AGGREGATE_20MM, 0d).put(RawMaterialType.AGGREGATE_10MM, 0d).put(RawMaterialType.SAND, 640d).put(RawMaterialType.WATER, 200d).put(RawMaterialType.ADMIXTURE, 0d).build()),
    RCC("RCC", ImmutableMap.<RawMaterialType,Double>builder().put(RawMaterialType.CEMENT, 160d).put(RawMaterialType.FLYASH, 160d).put(RawMaterialType.AGGREGATE_20MM, 743d).put(RawMaterialType.AGGREGATE_10MM, 530d).put(RawMaterialType.SAND, 850d).put(RawMaterialType.WATER, 148d).put(RawMaterialType.ADMIXTURE, 1.34d).build()),
    M7_5("M7.5", ImmutableMap.<RawMaterialType,Double>builder().put(RawMaterialType.CEMENT, 120d).put(RawMaterialType.FLYASH, 120d).put(RawMaterialType.AGGREGATE_20MM, 793d).put(RawMaterialType.AGGREGATE_10MM, 529d).put(RawMaterialType.SAND, 762d).put(RawMaterialType.WATER, 174d).put(RawMaterialType.ADMIXTURE, 1.44d).build()),
    M10("M10", ImmutableMap.<RawMaterialType,Double>builder().put(RawMaterialType.CEMENT, 130d).put(RawMaterialType.FLYASH, 130d).put(RawMaterialType.AGGREGATE_20MM, 755d).put(RawMaterialType.AGGREGATE_10MM, 503d).put(RawMaterialType.SAND, 760d).put(RawMaterialType.WATER, 167d).put(RawMaterialType.ADMIXTURE, 2.08).build()),
    M15_N("M15(N)", ImmutableMap.<RawMaterialType,Double>builder().put(RawMaterialType.CEMENT, 150d).put(RawMaterialType.FLYASH, 150d).put(RawMaterialType.AGGREGATE_20MM, 745d).put(RawMaterialType.AGGREGATE_10MM, 496d).put(RawMaterialType.SAND, 750d).put(RawMaterialType.WATER, 161d).put(RawMaterialType.ADMIXTURE, 2.7d).build()),
    M15_S("M15(S)", ImmutableMap.<RawMaterialType,Double>builder().put(RawMaterialType.CEMENT, 175d).put(RawMaterialType.FLYASH, 175d).put(RawMaterialType.AGGREGATE_20MM, 0d).put(RawMaterialType.AGGREGATE_10MM, 915d).put(RawMaterialType.SAND, 875d).put(RawMaterialType.WATER, 181d).put(RawMaterialType.ADMIXTURE, 2.1d).build()),
    CLSM2("CLSM2", ImmutableMap.<RawMaterialType,Double>builder().put(RawMaterialType.CEMENT, 120d).put(RawMaterialType.FLYASH, 200d).put(RawMaterialType.AGGREGATE_20MM, 749d).put(RawMaterialType.AGGREGATE_10MM, 500d).put(RawMaterialType.SAND, 695d).put(RawMaterialType.WATER, 180d).put(RawMaterialType.ADMIXTURE, 0d).build()),
    M20_N("M20(N)", ImmutableMap.<RawMaterialType,Double>builder().put(RawMaterialType.CEMENT, 230d).put(RawMaterialType.FLYASH, 100d).put(RawMaterialType.AGGREGATE_20MM, 747d).put(RawMaterialType.AGGREGATE_10MM, 497d).put(RawMaterialType.SAND, 751d).put(RawMaterialType.WATER, 156d).put(RawMaterialType.ADMIXTURE, 3.3d).build()),
    M25("M25", ImmutableMap.<RawMaterialType,Double>builder().put(RawMaterialType.CEMENT, 265d).put(RawMaterialType.FLYASH, 115d).put(RawMaterialType.AGGREGATE_20MM, 724d).put(RawMaterialType.AGGREGATE_10MM, 482d).put(RawMaterialType.SAND, 728d).put(RawMaterialType.WATER, 159d).put(RawMaterialType.ADMIXTURE, 3.8d).build()),
    M30("M30", ImmutableMap.<RawMaterialType,Double>builder().put(RawMaterialType.CEMENT, 306d).put(RawMaterialType.FLYASH, 131d).put(RawMaterialType.AGGREGATE_20MM, 726d).put(RawMaterialType.AGGREGATE_10MM, 484d).put(RawMaterialType.SAND, 697d).put(RawMaterialType.WATER, 167d).put(RawMaterialType.ADMIXTURE, 3.71d).build()),
    M40_PQC("M40(PQC)", ImmutableMap.<RawMaterialType,Double>builder().put(RawMaterialType.CEMENT, 309d).put(RawMaterialType.FLYASH, 233d).put(RawMaterialType.AGGREGATE_20MM, 698d).put(RawMaterialType.AGGREGATE_10MM, 465d).put(RawMaterialType.SAND, 616d).put(RawMaterialType.WATER, 166d).put(RawMaterialType.ADMIXTURE, 3.25d).build());
    
    private final static ThreadLocal<NumberFormat> numberFormat = new ThreadLocal<NumberFormat>() {
        @Override
        public NumberFormat initialValue() {
            return new DecimalFormat("#0.00");
        }
    };
    
    private final String name;
    private final Map<RawMaterialType,Double> rawMaterialInOneCubicMeter;
    private ConcreteMixture(String name,Map<RawMaterialType,Double> rawMaterialInOneCubicMeter) {
        this.name = name;
        this.rawMaterialInOneCubicMeter = rawMaterialInOneCubicMeter;
    }
    public Double getRequiredAmount(RawMaterialType rawMaterialType,Double mixInCuMeter){
        return mixInCuMeter * rawMaterialInOneCubicMeter.getOrDefault(rawMaterialType,0d);
    }
    public String getRequiredAmountAsString(RawMaterialType rawMaterialType,Double mixInCuMeter){
        return numberFormat.get().format(getRequiredAmount(rawMaterialType, mixInCuMeter));
    }
    public String printRequiredAmount(Double mixInCuMeter){
        final StringBuffer buf = new StringBuffer();
        buf.append(RawMaterialType.CEMENT.getName()).append(": ").append(getRequiredAmountAsString(RawMaterialType.CEMENT, mixInCuMeter)).append(" ").append(RawMaterialType.CEMENT.getUnit().getName()).append("\n");
        buf.append(RawMaterialType.FLYASH.getName()).append(": ").append(getRequiredAmountAsString(RawMaterialType.FLYASH, mixInCuMeter)).append(" ").append(RawMaterialType.FLYASH.getUnit().getName()).append("\n");
        buf.append(RawMaterialType.AGGREGATE_20MM.getName()).append(": ").append(getRequiredAmountAsString(RawMaterialType.AGGREGATE_20MM, mixInCuMeter)).append(" ").append(RawMaterialType.AGGREGATE_20MM.getUnit().getName()).append("\n");
        buf.append(RawMaterialType.AGGREGATE_10MM.getName()).append(": ").append(getRequiredAmountAsString(RawMaterialType.AGGREGATE_10MM, mixInCuMeter)).append(" ").append(RawMaterialType.AGGREGATE_10MM.getUnit().getName()).append("\n");
        buf.append(RawMaterialType.SAND.getName()).append(": ").append(getRequiredAmountAsString(RawMaterialType.SAND, mixInCuMeter)).append(" ").append(RawMaterialType.SAND.getUnit().getName()).append("\n");
        buf.append(RawMaterialType.WATER.getName()).append(": ").append(getRequiredAmountAsString(RawMaterialType.WATER, mixInCuMeter)).append(" ").append(RawMaterialType.WATER.getUnit().getName()).append("\n");
        buf.append(RawMaterialType.ADMIXTURE.getName()).append(": ").append(getRequiredAmountAsString(RawMaterialType.ADMIXTURE, mixInCuMeter)).append(" ").append(RawMaterialType.ADMIXTURE.getUnit().getName());
        return buf.toString();
    }
    public Map<RawMaterialType,Double> getRequiredAmount(Double mixInCuMeter){
        return ImmutableMap.<RawMaterialType,Double>builder().put(RawMaterialType.CEMENT, getRequiredAmount(RawMaterialType.CEMENT, mixInCuMeter)).put(RawMaterialType.FLYASH, getRequiredAmount(RawMaterialType.FLYASH, mixInCuMeter)).put(RawMaterialType.AGGREGATE_20MM, getRequiredAmount(RawMaterialType.AGGREGATE_20MM, mixInCuMeter)).put(RawMaterialType.AGGREGATE_10MM, getRequiredAmount(RawMaterialType.AGGREGATE_10MM, mixInCuMeter)).put(RawMaterialType.SAND, getRequiredAmount(RawMaterialType.SAND, mixInCuMeter)).put(RawMaterialType.WATER, getRequiredAmount(RawMaterialType.WATER, mixInCuMeter)).put(RawMaterialType.ADMIXTURE, getRequiredAmount(RawMaterialType.ADMIXTURE, mixInCuMeter)).build();
    }
    public Map<String,String> getRequiredAmountAsString(Double mixInCuMeter){
        return ImmutableMap.<String,String>builder().put(RawMaterialType.CEMENT.getName(), getRequiredAmountAsString(RawMaterialType.CEMENT, mixInCuMeter)).put(RawMaterialType.FLYASH.getName(), getRequiredAmountAsString(RawMaterialType.FLYASH, mixInCuMeter)).put(RawMaterialType.AGGREGATE_20MM.getName(), getRequiredAmountAsString(RawMaterialType.AGGREGATE_20MM, mixInCuMeter)).put(RawMaterialType.AGGREGATE_10MM.getName(), getRequiredAmountAsString(RawMaterialType.AGGREGATE_10MM, mixInCuMeter)).put(RawMaterialType.SAND.getName(), getRequiredAmountAsString(RawMaterialType.SAND, mixInCuMeter)).put(RawMaterialType.WATER.getName(), getRequiredAmountAsString(RawMaterialType.WATER, mixInCuMeter)).put(RawMaterialType.ADMIXTURE.getName(), getRequiredAmountAsString(RawMaterialType.ADMIXTURE, mixInCuMeter)).build();
    }
}
