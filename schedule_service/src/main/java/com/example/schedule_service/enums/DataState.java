package com.example.schedule_service.enums;

public enum DataState {
    ALL("ALL", -1),
    ACTIVE("Active", 1),
    INACTIVE("Inactive", 0);

    private final String name;

    private final int value;

    private DataState(String name, int value) {
        this.name = name;
        this.value = value;
    }

    public static DataState valueFromName(String name) {
        for (DataState state : DataState.values()) {
            if (state.getName().equals(name)) {
                return state;
            }
        }
        throw new RuntimeException();
    }

    public String getName() {
        return name;
    }

    public int getValue() {
        return value;
    }

    // public static List<String> getValues() {
    //     List<String> states = new ArrayList<>();
    //     for (DataState state : DataState.values()) {
    //         states.add(state.name);
    //     }
    //     return states;
    // }
}
