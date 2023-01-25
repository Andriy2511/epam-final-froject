package com.example.finalproject.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class GenericDAO<T> {
    protected abstract T mapToEntity(ResultSet rs) throws SQLException;

    protected abstract PreparedStatement mapFromEntity(PreparedStatement preparedStatement, T obj) throws SQLException;
}
