package ie.ucd.comp20050.entity;

/**
 * Interface to handle position and other internal data of entities.
 */
public interface IEntity {

    /**
     * Method to retrieve the X position of some entity
     * @return integer x-position
     */
    int getPosX();

    /**
     * Method to retrieve the Y position of some entity
     * @return integer y-position
     */
    int getPosY();

    /**
     * Method to set the X position of some entity
     * @param value integer x-position
     */
    void setPosX(int value);

    /**
     * Method to set the Y position of some entity
     * @param value integer y-position
     */
    void setPosY(int value);

}
