/**
 * The set of vehicle types a driver's vehicle may be. Used by IDriver in
 * place of a free-text vehicle type string, so vehicle types are validated
 * by the type system instead of by string matching.
 * VehicleType: This enum represents the set of vehicle types a driver’s vehicle may be:
SEDAN, LUXURY SEDAN, SUV, and VAN. It is used by the Driver class instead of a free-text
string, so a vehicle’s type is validated by the type system rather than by string matching.
 */
public enum VehicleType {
    SEDAN,
    LUXURY_SEDAN,
    SUV,
    VAN
}
