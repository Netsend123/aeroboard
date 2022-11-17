package aero.board.dao;

import aero.board.config.SpringConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.*;

@Component
public class SearchDAO {
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();

        dataSource.setDriverClassName("org.postgresql.Driver");
        dataSource.setUrl("jdbc:postgresql://localhost:5432/aerobase");
        dataSource.setUsername("postgres");
        dataSource.setPassword("postgres");

        return dataSource;
    }


    public JdbcTemplate jdbcTemplate() {
        return new JdbcTemplate(dataSource());
    }

    private JdbcTemplate jdbcTemplate = jdbcTemplate();



    public void saveSearch(String searsh) {
        jdbcTemplate.update("INSERT INTO AIRPORTLIST VALUES(1,?)", searsh);
    }
}
