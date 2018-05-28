package flexdb;

/**
 * FlexDbType, enum,
 * This represents the enum that contains the types from Flexible Database
 *
 * @author biologyiswell (18/05/2018 17:35)
 * @since 0.1
 */
public enum FlexDbType {

    // @Note This flexible database type, single, represents a database that contains a single connection, this type is
    // used to databases that make low quantity of operations
    // @since 0.1
    SINGLE,

    // @Note This flexible database type, pooled, represents a database that cnotains a pool of connections, this type is
    // used to databases that make big quantity of operations and make the processment of informations more faster
    // @since 0.1
    POOLED
}
