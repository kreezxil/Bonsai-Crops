package com.kreezcraft.bonsaicrops.api;

public interface IBonsaiSoilRegistry {
    void registerBonsaiSoilIntegration(IBonsaiIntegration integrator, IBonsaiSoil soil);
}
