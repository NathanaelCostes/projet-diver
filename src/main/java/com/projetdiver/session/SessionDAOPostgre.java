package com.projetdiver.session;

import com.projetdiver.diver.Diver;
import com.projetdiver.session.invitation.Invitation;
import com.projetdiver.diver.DiverDAO;

import java.util.ArrayList;
import java.sql.*;
import java.util.Objects;

//import io.github.cdimascio.dotenv.Dotenv;


public class SessionDAOPostgre extends SessionDAO {

    /**
     * Default constructor
     */
    public SessionDAOPostgre() {}

    /** dotenv to load informations from the .env */
    //private Dotenv dotenv = Dotenv.load();

    private Connection connection;

    /** User of the database to get in the .env */
    private final String DB_USER = "postgres"; //dotenv.get("DB_USER");

    /** Password of the database to get in the .env */
    private final String DB_PASSWORD = "postgres"; //dotenv.get("DB_PASSWORD");

    /** URL of the database to get in the .env */
    private final String DB_URL = "jdbc:postgresql://localhost:5432/projet_diver_db"; //dotenv.get("DB_URL");

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

    @Override
    public Session getSession(Integer sessionId) {
        try {
            connection();

            // Use a prepared statement to avoid SQL injection
            String sql = "SELECT * FROM session WHERE sessionId=?;";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setInt(1, sessionId);
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
                } else {
                    return null;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        } finally {
            closeConnection();
        }
    }

    @Override
    public Session getSession(Diver owner, String title) {
        try {
            connection();

            // Use a prepared statement to avoid SQL injection
            String sql = "SELECT * FROM session WHERE owner=? AND title=?;";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setInt(1, owner.getDiverId());
                statement.setString(2, title);
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
                } else {
                    return null;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        } finally {
            closeConnection();
        }
    }

    @Override
    public ArrayList<Session> getAllSessionsWhereDiverIsOwner(Diver owner){
        try {
            connection();

            // Use a prepared statement to avoid SQL injection
            String sql = "SELECT * FROM session WHERE owner=?;";

            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setInt(1, owner.getDiverId());
                ResultSet resultSet = statement.executeQuery();

                ArrayList<Session> allSessions = new ArrayList<>();

                while (resultSet.next()) {
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
                    allSessions.add(session);
                }

                resultSet.close();
                return allSessions;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        } finally {
            closeConnection();
        }
    }

    @Override
    public ArrayList<Session> getAllSessionsWhereDiverIsInvited(Diver diver, boolean pending) {
        try {
            connection();

            // Use a prepared statement to avoid SQL injection
            String sql = "SELECT * FROM session INNER JOIN invitation ON session.sessionId = invitation.sessionId WHERE invitation.receiver=? AND invitation.pending=?;";

            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setInt(1, diver.getDiverId());
                statement.setBoolean(2, pending);
                ResultSet resultSet = statement.executeQuery();

                ArrayList<Session> allSessions = new ArrayList<>();

                while (resultSet.next()) {
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
                    allSessions.add(session);
                }

                resultSet.close();
                return allSessions;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        } finally {
            closeConnection();
        }
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
        try {
            connection();

            // Use a prepared statement to avoid SQL injection
            String sql = "UPDATE session SET title=?, date=?, comment=?, duration=?, temp=?, depth=? WHERE sessionId=?;";
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
            String sql = "DELETE FROM session WHERE sessionId=?;";
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
            String sql = "SELECT * FROM invitation WHERE sessionId=?;";
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
    boolean acceptInvitation(Invitation invitation) {
        try {
            connection();

            // Use a prepared statement to avoid SQL injection
            String sql = "UPDATE invitation SET pending=? WHERE sessionId=? AND receiver=?;";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setBoolean(1, false);
                statement.setInt(2, invitation.getSession().getSessionId());
                statement.setInt(3, invitation.getReceiver().getDiverId());
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
            String sql = "DELETE FROM invitation WHERE sessionId=? AND receiver=?;";
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
