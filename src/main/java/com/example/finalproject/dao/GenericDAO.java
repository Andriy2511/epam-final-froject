package com.example.finalproject.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Contains methods to convert resultset to objects and vice versa
 * @param <T> object type
 */
public abstract class GenericDAO<T> {
    /**
     * Converts result set to the entity
     * @param rs ResultSet
     * @return an instance of the object
     */
    protected abstract T mapToEntity(ResultSet rs) throws SQLException;

    /**
     * Sets value to PreparedStatement from the object
     * @param preparedStatement PreparedStatement
     * @param obj object from a model package
     */
    protected abstract void mapFromEntity(PreparedStatement preparedStatement, T obj) throws SQLException;
}
