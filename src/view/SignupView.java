package view;

import interface_adapter.SignupController;
import interface_adapter.SignupState;
import interface_adapter.SignupViewModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class SignupView extends JPanel implements ActionListener, PropertyChangeListener {
    public final String viewName = "sign up";

    private final SignupViewModel signupViewModel;
    private final JTextField usernameInputField = new JTextField(15);
    private final JPasswordField passwordInputField = new JPasswordField(15);
    private final JPasswordField repeatPasswordInputField = new JPasswordField(15);
    private final SignupController signupController;

    private final JButton signUp;
    private final JButton cancel;

    public SignupView(SignupController controller, SignupViewModel signupViewModel) {
        // Step 3 Q1
        // name - signupViewModel
        // initialized - below in this.signupViewModel = signupViewModel
        // class that instantiates SignUpView - SignUpView

        this.signupController = controller;
        this.signupViewModel = signupViewModel;
        signupViewModel.addPropertyChangeListener(this);

        JLabel title = new JLabel(signupViewModel.TITLE_LABEL);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        LabelTextPanel usernameInfo = new LabelTextPanel(
                new JLabel(signupViewModel.USERNAME_LABEL), usernameInputField);
        LabelTextPanel passwordInfo = new LabelTextPanel(
                new JLabel(signupViewModel.PASSWORD_LABEL), passwordInputField);
        LabelTextPanel repeatPasswordInfo = new LabelTextPanel(
                new JLabel(signupViewModel.REPEAT_PASSWORD_LABEL), repeatPasswordInputField);

        JPanel buttons = new JPanel();
        signUp = new JButton(signupViewModel.SIGNUP_BUTTON_LABEL);
        buttons.add(signUp);
        cancel = new JButton(signupViewModel.CANCEL_BUTTON_LABEL);
        buttons.add(cancel);

        signUp.addActionListener(
                // This creates an anonymous subclass of ActionListener and instantiates it.
                new ActionListener() {
                    public void actionPerformed(ActionEvent evt) {
                        // This is the method that gets called when an action event happens,
                        // like clicking a button
                        // The 'ActionEvent evt' parameter contains information about the event.
                        if (evt.getSource().equals(signUp)) {
                            SignupState currentState = signupViewModel.getState();
                            String username = currentState.getUsername();
                            String password = currentState.getPassword();
                            String repeatPassword = currentState.getRepeatPassword();
                            signupController.execute(username, password, repeatPassword);
                            // this line checks if the source of the event was the 'signUp' object.
                            // If Sign-Up was clicked.
                            // If the above condition is true (ie, if signUp was clicked),
                            // this line calls a method named "execute" from "signupController".
//                            signupController.execute(usernameInputField.getText(),
//                                    // retrieves whatever text has been typed into a field named
//                                    // "usernameInputField".
//                                    String.valueOf(passwordInputField.getPassword()),
//                                    String.valueOf(repeatPasswordInputField.getPassword()));
                                    // lines retrieve whatever has been typed into fields
                            // named "passwordInputField" and "repeatPasswordInputField",
                            // respectively, and convert them to strings using String.valueOf().
                        }
                    }
                }
        );
        cancel.addActionListener(this);

        // This makes a new KeyListener implementing class, instantiates it, and
        // makes it listen to keystrokes in the usernameInputField.
        //
        // Notice how it has access to instance variables in the enclosing class!
        usernameInputField.addKeyListener(
                new KeyListener() {
                    // anonymous subclass of Key Listener and instantiates
                    @Override
                    public void keyTyped(KeyEvent e) {
                        SignupState currentState = signupViewModel.getState();
                        // retrieves current state of sign-up process
                        currentState.setUsername(usernameInputField.getText() + e.getKeyChar());
                        // sets username in that state object with current
                        // text present in input field plus character just typed
                        // e.getKeyChar() returns character corresponding to key just typed.
                        // So essentially it updates username in state as user types
                        signupViewModel.setState(currentState);
                        // it sets updated state back into view model
                    }

                    @Override
                    public void keyPressed(KeyEvent e) {
                    }

                    @Override
                    public void keyReleased(KeyEvent e) {
                    }
                });
        // Add KeyListener for passwordInputField
        passwordInputField.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                SignupState currentState = signupViewModel.getState();
                currentState.setPassword(String.valueOf(passwordInputField.getPassword()) + e.getKeyChar());
                signupViewModel.setState(currentState);
            }

            @Override
            public void keyPressed(KeyEvent e) {
            }

            @Override
            public void keyReleased(KeyEvent e) {
            }
        });

// Add KeyListener for repeatPasswordInputField
        repeatPasswordInputField.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                SignupState currentState = signupViewModel.getState();
                currentState.setRepeatPassword(String.valueOf(repeatPasswordInputField.getPassword()) + e.getKeyChar());
                signupViewModel.setState(currentState);
            }

            @Override
            public void keyPressed(KeyEvent e) {
            }

            @Override
            public void keyReleased(KeyEvent e) {
            }
        });

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        this.add(title);
        this.add(usernameInfo);
        this.add(passwordInfo);
        this.add(repeatPasswordInfo);
        this.add(buttons);
    }

    /**
     * React to a button click that results in evt.
     */
    public void actionPerformed(ActionEvent evt) {
        System.out.println("Cancel not implemented yet.");
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        SignupState state = (SignupState) evt.getNewValue();
        if (state.getUsernameError() != null) {
            JOptionPane.showMessageDialog(this, state.getUsernameError());
        }
    }
}