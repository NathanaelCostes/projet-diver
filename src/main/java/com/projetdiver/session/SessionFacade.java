package com.projetdiver.session;

import com.projetdiver.dao.PostgreDAOFactory;
import com.projetdiver.diver.Diver;
import com.projetdiver.diver.DiverFacade;
import com.projetdiver.session.exceptions.*;

import java.util.ArrayList;

public class SessionFacade {

    //TODO: Savoir si je dois passer par une facade à faire plus tard dans tout les cas
    //TODO: Savoir si NotConnectedException est la bonne exception à lever
    //TODO: Savoir si je dois faire refactoriser les exceptions dans d'autres packages

    /** the instance of the SessionFacade */
    private static SessionFacade instance;

    /** the instance of the SessionDAO */
    private final static SessionDAO sessionDAO = PostgreDAOFactory.getInstance().createSessionDAO();

    /**
     * Default constructor
     */
    private SessionFacade() {}

    /**
     * get the instance of the SessionFacade
     */
    public static SessionFacade getInstance() {

        if(SessionFacade.instance == null){
            SessionFacade.instance = new SessionFacade();
        }

        return SessionFacade.instance;
    }

    /**
     * @return the current diver
     */
    public Diver getCurrentDiver() {
        return DiverFacade.getInstance().getCurrentDiver();
    }

    //TODO: Quand getId sera implémenté en tant que Integer, il faudra modifier cette méthode
    private void checkDiverIsConnected() throws NotConnectedException {
        if(getCurrentDiver() == null){
            throw new NotConnectedException();
        } /* else if(getCurrentDiver().getId() == null){
            throw new NotConnected();
        } */
    }

    /**
     * @param sessionId the id of the session
     * @return the session with the id
     * @throws NotConnectedException if the diver is not connected
     * @throws SessionNotFoundException if the session is not found
     */
    public Session getSession(Integer sessionId) throws NotConnectedException, SessionNotFoundException {

        checkDiverIsConnected();

        Session session = sessionDAO.getSession(sessionId);

        if(session == null){
            throw new SessionNotFoundException();
        }

        return session;
    }

    /**
     * @param owner the owner of the session
     * @param title the title of the session
     * @return the session with the owner and the title
     * @throws NotConnectedException if the diver is not connected
     * @throws SessionNotFoundException if the session is not found
     */
    public Session getSession(Diver owner, String title) throws NotConnectedException, SessionNotFoundException {

        checkDiverIsConnected();

        Session session = sessionDAO.getSession(owner, title);

        if(session == null){
            throw new SessionNotFoundException();
        }

        return session;
    }


    /**
     * @return the sessions where the current diver is the owner or accepted the invitation
     * @throws NotConnectedException if the diver is not connected
     */
    public ArrayList<Session> getAllSessions() throws NotConnectedException {

        checkDiverIsConnected();

        ArrayList<Session> sessions = sessionDAO.getAllSessionsWhereDiverIsOwner(getCurrentDiver());
        sessions.addAll(sessionDAO.getAllSessionsWhereDiverIsInvited(getCurrentDiver(), false));

        return sessions;
    }

    /**
     * Create a session
     * @param session the session to create
     * @return if the session is created
     * @throws NotConnectedException if the diver is not connected
     * @throws SessionAlreadyExistsException if the session with the same title for the same diver already exists
     */
    public boolean createSession(Session session) throws NotConnectedException, SessionAlreadyExistsException {

        checkDiverIsConnected();

        if (sessionDAO.getSession(session.getOwner(), session.getTitle()) != null) {
            throw new SessionAlreadyExistsException();
        }

        return sessionDAO.createSession(session);
    }

    public boolean deleteSessionCascade(Session session){
        try{
            this.getAllInvitationsToSession(session).forEach(invitation -> {
                try {
                    deleteInvitation(invitation);
                } catch (NotConnectedException | SessionNotFoundException | DiverNotFoundException | InvitationNotFoundException e) {
                    e.printStackTrace();
                }
            });
            deleteSession(session);
        }
        catch (NotConnectedException | SessionNotFoundException e){
            e.printStackTrace();

        }
        return false;
    }

    /**
     * Modify a session
     * @param session the session to modify
     * @return if the session is modified
     * @throws NotConnectedException if the diver is not connected
     * @throws SessionNotFoundException if the session is not found
     * @throws SessionAlreadyExistsException if the session with the same title for the same diver already exists
     */
    public boolean modifySession(Session session) throws NotConnectedException,
            SessionNotFoundException,
            SessionAlreadyExistsException {

        checkDiverIsConnected();

        // If the session already exists with the same title for the same diver but not the same session
        Session sessionWithSameTitle = sessionDAO.getSession(session.getOwner(), session.getTitle());
        if (sessionWithSameTitle != null) {
            if (!sessionWithSameTitle.getSessionId().equals(session.getSessionId())) {
                throw new SessionAlreadyExistsException("Session already exists with the same title");
            }
        }

        if (sessionDAO.getSession(session.getSessionId()) == null) {
            throw new SessionNotFoundException();
        }

        return sessionDAO.modifySession(session);
    }

    /**
     * Delete a session
     * @param session the session to delete
     * @return if the session is deleted
     * @throws NotConnectedException if the diver is not connected
     * @throws SessionNotFoundException if the session is not found
     */
    public boolean deleteSession(Session session) throws NotConnectedException, SessionNotFoundException {

        checkDiverIsConnected();

        if (sessionDAO.getSession(session.getSessionId()) == null) {
            throw new SessionNotFoundException();
        }

        return sessionDAO.deleteSession(session);
    }

    /**
     * Get the invitation
     * @param session the session of the invitation
     * @param receiver the receiver of the invitation
     * @return the invitation
     * @throws NotConnectedException if the diver is not connected
     * @throws SessionNotFoundException if the session is not found
     * @throws DiverNotFoundException if the diver is not found
     * @throws InvitationNotFoundException if the invitation is not found
     */
    public Invitation getInvitation(Session session, Diver receiver) throws NotConnectedException,
            SessionNotFoundException,
            DiverNotFoundException,
            InvitationNotFoundException {

        checkDiverIsConnected();

        Session sessionFetched = sessionDAO.getSession(session.getSessionId());

        if (sessionFetched == null) {
            throw new SessionNotFoundException();
        }

        Diver receiverFetched = PostgreDAOFactory.getInstance().createDiverDAO().getDiver(receiver.getId());

        if (receiverFetched == null) {
            throw new DiverNotFoundException();
        }

        Invitation invitationFetched = sessionDAO.getInvitation(session, receiver);

        if (invitationFetched == null) {
            throw new InvitationNotFoundException();
        }

        return invitationFetched;
    }

    /**
     * Get all the invitations to the current diver
     * @return all the invitations to the current diver
     * @throws NotConnectedException if the diver is not connected
     */
    public ArrayList<Invitation> getAllInvitationsToDiver() throws NotConnectedException {

        checkDiverIsConnected();

        return sessionDAO.getAllInvitationsToDiver(getCurrentDiver());
    }

    /**
     * Get all the invitations to the session
     * @param session the session of the invitations
     * @return all the invitations to the session
     * @throws NotConnectedException if the diver is not connected
     * @throws SessionNotFoundException if the session is not found
     */
    public ArrayList<Invitation> getAllInvitationsToSession(Session session) throws NotConnectedException, SessionNotFoundException {

        checkDiverIsConnected();

        Session sessionFetched = sessionDAO.getSession(session.getSessionId());

        if (sessionFetched == null) {
            throw new SessionNotFoundException();
        }

        return sessionDAO.getAllInvitationsToSession(session);
    }

    /**
     * Create an invitation
     * @param invitation the invitation to create
     * @return if the invitation is created
     * @throws NotConnectedException if the diver is not connected
     * @throws SessionNotFoundException if the session is not found
     * @throws DiverNotFoundException if the diver is not found
     * @throws InvitationAlreadyExistsException if the invitation already exists
     */
    public boolean createInvitation(Invitation invitation) throws NotConnectedException,
            SessionNotFoundException,
            DiverNotFoundException,
            InvitationAlreadyExistsException {

        checkDiverIsConnected();

        Session sessionFetched = sessionDAO.getSession(invitation.getSession().getSessionId());

        if (sessionFetched == null) {
            throw new SessionNotFoundException();
        }

        Diver receiverFetched = PostgreDAOFactory.getInstance().createDiverDAO().getDiver(invitation.getReceiver().getId());

        if (receiverFetched == null) {
            throw new DiverNotFoundException();
        }

        Invitation invitationFetched = sessionDAO.getInvitation(invitation.getSession(), invitation.getReceiver());

        if (invitationFetched != null) {
            throw new InvitationAlreadyExistsException();
        }

        return sessionDAO.createInvitation(invitation);
    }

    /**
     * Accept an invitation
     * @param invitation the invitation to accept
     * @return if the invitation is accepted
     * @throws NotConnectedException if the diver is not connected
     * @throws SessionNotFoundException if the session is not found
     * @throws DiverNotFoundException if the diver is not found
     * @throws InvitationNotFoundException if the invitation is not found
     */
    public boolean acceptInvitation(Invitation invitation) throws NotConnectedException,
            SessionNotFoundException,
            DiverNotFoundException,
            InvitationNotFoundException {

        checkDiverIsConnected();

        Session sessionFetched = sessionDAO.getSession(invitation.getSession().getSessionId());

        if (sessionFetched == null) {
            throw new SessionNotFoundException();
        }

        Diver receiverFetched = PostgreDAOFactory.getInstance().createDiverDAO().getDiver(invitation.getReceiver().getId());

        if (receiverFetched == null) {
            throw new DiverNotFoundException();
        }

        Invitation invitationFetched = sessionDAO.getInvitation(invitation.getSession(), invitation.getReceiver());

        if (invitationFetched == null) {
            throw new InvitationNotFoundException();
        }

        return sessionDAO.acceptInvitation(invitation);
    }

    /**
     * Delete an invitation
     * @param invitation the invitation to delete
     * @return if the invitation is deleted
     * @throws NotConnectedException if the diver is not connected
     * @throws SessionNotFoundException if the session is not found
     * @throws DiverNotFoundException if the diver is not found
     * @throws InvitationNotFoundException if the invitation is not found
     */
    public boolean deleteInvitation(Invitation invitation) throws NotConnectedException,
            SessionNotFoundException,
            DiverNotFoundException,
            InvitationNotFoundException {

        checkDiverIsConnected();

        Session sessionFetched = sessionDAO.getSession(invitation.getSession().getSessionId());

        if (sessionFetched == null) {
            throw new SessionNotFoundException();
        }

        Diver receiverFetched = PostgreDAOFactory.getInstance().createDiverDAO().getDiver(invitation.getReceiver().getId());

        if (receiverFetched == null) {
            throw new DiverNotFoundException();
        }

        Invitation invitationFetched = sessionDAO.getInvitation(invitation.getSession(), invitation.getReceiver());

        if (invitationFetched == null) {
            throw new InvitationNotFoundException();
        }

        return sessionDAO.deleteInvitation(invitation);
    }

}