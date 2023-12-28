package com.projetdiver.session;

import com.projetdiver.diver.Diver;
import com.projetdiver.session.invitation.Invitation;
import com.projetdiver.diver.DiverDAO;

import java.util.ArrayList;
import java.sql.*;
import java.util.Objects;

import io.github.cdimascio.dotenv.Dotenv;


public class SessionDAOPostgre extends SessionDAO{

    /**
     * Default constructor
     */
    public SessionDAOPostgre() {}

    /** dotenv to load informations from the .env */
    private Dotenv dotenv = Dotenv.load();

    private Connection connection;

    /** User of the database to get in the .env */
    private final String DB_USER = dotenv.get("DB_USER");

    /** Password of the database to get in the .env */
    private final String DB_PASSWORD = dotenv.get("DB_PASSWORD");

    /** URL of the database to get in the .env */
    private final String DB_URL = dotenv.get("DB_URL");

    /**
     * Connect to the database using the informations in the .env
     */
    private void connection() {
        try {
            this.connection = DriverManager.getConnection(this.DB_URL, this.DB_USER, this.DB_PASSWORD);
            this.connection.isValid(2);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void closeConnection() {
        // Close the connection
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private Session getSession(Object object) {

        try {
            connection();

            // Use a prepared statement to avoid SQL injection
            String sql;

            // In the case where objects is an Integer
            if (object instanceof Integer) {
                sql = "SELECT * FROM session WHERE sessionid=?;";
            }
            // In the case where objects is a String
            else {
                sql = "SELECT * FROM session WHERE title=?;";
            }

            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                if(object instanceof Integer) {
                    statement.setInt(1, (Integer) object);
                } else {
                    statement.setString(1, (String) object);
                }
                ResultSet resultSet = statement.executeQuery();

                if (resultSet.next()) {
                    Session session = new Session(
                            resultSet.getInt("sessionId"),
                        resultSet.getString("title"),
                        resultSet.getDate("date"),
                        resultSet.getString("comment"),
                        resultSet.getFloat("duration"),
                        resultSet.getInt("temp"),
                        resultSet.getInt("depth"),
                        DiverDAO.getInstance().getDiver(resultSet.getInt("owner"))
                    );

                    resultSet.close();

                    return session;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            closeConnection();
        }
        return null;
    }

    @Override
    public Session getSession(Integer sessionId) {
        return getSession((Object) sessionId);
    }

    @Override
    public Session getSession(String title) {
        return getSession((Object) title);
    }

    @Override
    public boolean createSession(Session session) {

        try {
            connection();

            // Use a prepared statement to avoid SQL injection
            String sql = "INSERT INTO session (title, date, comment, duration, temp, depth, owner) VALUES (?, ?, ?, ?, ?, ?, ?);";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, session.getTitle());
                statement.setDate(2, session.getDate());
                statement.setString(3, session.getComment());
                statement.setFloat(4, session.getDuration());
                statement.setInt(5, session.getTemp());
                statement.setInt(6, session.getDepth());
                statement.setInt(7, session.getOwner().getDiverId());
                statement.executeUpdate();

                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            closeConnection();
        }
    }

    @Override
    public boolean modifySession(Session session) {
        if (session.getSessionId() == null) {
            return false;
        }

        try {
            connection();

            // Use a prepared statement to avoid SQL injection
            String sql = "UPDATE session SET title=?, date=?, comment=?, duration=?, temp=?, depth=? WHERE sessionid=?;";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, session.getTitle());
                statement.setDate(2, session.getDate());
                statement.setString(3, session.getComment());
                statement.setFloat(4, session.getDuration());
                statement.setInt(5, session.getTemp());
                statement.setInt(6, session.getDepth());
                statement.setInt(7, session.getSessionId());
                statement.executeUpdate();

                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            closeConnection();
        }
    }

    @Override
    public boolean deleteSession(Session session) {
        try {
            connection();

            // Use a prepared statement to avoid SQL injection
            String sql = "DELETE FROM session WHERE sessionid=?;";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setInt(1, session.getSessionId());
                statement.executeUpdate();
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            closeConnection();
        }
    }

    @Override
    public Invitation getInvitation(Session session, Diver receiver) {
        try {
            connection();

            // Use a prepared statement to avoid SQL injection
            String sql = "SELECT * FROM invitation WHERE sessionId=? AND receiver=?;";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setInt(1, session.getSessionId());
                statement.setInt(2, receiver.getDiverId());
                ResultSet resultSet = statement.executeQuery();

                if (resultSet.next()) {
                    Invitation invitation = new Invitation(
                            getSession(resultSet.getInt("sessionId")),
                            DiverDAO.getInstance().getDiver(resultSet.getInt("receiver")),
                            resultSet.getBoolean("pending")
                    );

                    resultSet.close();
                    System.out.println(invitation);
                    return invitation;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnection();
        }
        return null;
    }

    @Override
    ArrayList<Invitation> getAllInvitationsToDiver(Diver receiver) {
        try {
            connection();

            // Use a prepared statement to avoid SQL injection
            String sql = "SELECT * FROM invitation WHERE receiver=?;";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setInt(1, receiver.getDiverId());
                ResultSet resultSet = statement.executeQuery();

                ArrayList<Invitation> allInvitations = new ArrayList<>();

                while (resultSet.next()) {
                    Invitation invitation = new Invitation(
                            getSession(resultSet.getInt("sessionId")),
                            DiverDAO.getInstance().getDiver(resultSet.getInt("receiver")),
                            resultSet.getBoolean("pending")
                    );
                    allInvitations.add(invitation);
                }

                resultSet.close();
                return allInvitations;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnection();
        }
        return null;
    }

    @Override
    ArrayList<Invitation> getAllInvitationsToSession(Session session) {
        try {
            connection();

            // Use a prepared statement to avoid SQL injection
            String sql = "SELECT * FROM invitation WHERE sessionid=?;";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setInt(1, session.getSessionId());
                ResultSet resultSet = statement.executeQuery();

                ArrayList<Invitation> allInvitations = new ArrayList<>();

                while (resultSet.next()) {
                    Invitation invitation = new Invitation(
                            getSession(resultSet.getInt("sessionId")),
                            DiverDAO.getInstance().getDiver(resultSet.getInt("receiver")),
                            resultSet.getBoolean("pending")
                    );
                    allInvitations.add(invitation);
                }

                resultSet.close();
                return allInvitations;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnection();
        }
        return null;
    }

    @Override
    boolean createInvitation(Invitation invitation) {
        try {
            connection();

            // Use a prepared statement to avoid SQL injection
            String sql = "INSERT INTO invitation (sessionId, receiver) VALUES (?, ?);";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setInt(1, invitation.getSession().getSessionId());
                statement.setInt(2, invitation.getReceiver().getDiverId());
                statement.executeUpdate();

                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            closeConnection();
        }
    }

    @Override
    boolean deleteInvitation(Invitation invitation) {
        try {
            connection();

            // Use a prepared statement to avoid SQL injection
            String sql = "DELETE FROM invitation WHERE sessionid=? AND receiver=?;";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setInt(1, invitation.getSession().getSessionId());
                statement.setInt(2, invitation.getReceiver().getDiverId());
                statement.executeUpdate();

                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            closeConnection();
        }
    }

}
