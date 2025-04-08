import java.awt.*;
import java.util.*;
import javax.swing.*;
public class main {
    private String currentUser = "";
    private HashMap<String, UserProfile> users = new HashMap<>();
    //UI components
    JLabel xpLabel;
    JLabel coinsLabel;
    JLabel levelLabel;

    JFrame frame;

    public main()
    {
        frame = new JFrame("FIT-Quest - Innovation in Nutrition");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(450,400);
        frame.setLocationRelativeTo(null);
        frame.getContentPane().setBackground(new Color(240,248,255));
        UIManager.put("Button.font",new Font("Segoe UI Emoji",Font.BOLD,14));
        UIManager.put("Label.font",new Font("Segoe UI Emoji",Font.PLAIN,14));
        UIManager.put("TextField.font",new Font("Segoe UI Emoji",Font.PLAIN,14));
        showLoginScreen();
        frame.setVisible(true);

    }
    private void showLoginScreen(){
        JPanel panel = new JPanel(new GridLayout(5, 1, 10, 10)); // Increased to 5 for password
        panel.setBorder(BorderFactory.createEmptyBorder(30, 40, 30, 40));
        panel.setBackground(new Color(240, 248, 255));
    
        JLabel title = new JLabel("Welcome to FIT-Quest!", JLabel.CENTER);
        title.setFont(new Font("Jetbrains Mono", Font.BOLD, 20));
    
        JTextField nameField = new JTextField();
        nameField.setFont(new Font("Jetbrains Mono", Font.PLAIN, 14)); // Increase font size
    
        JPasswordField passwordField = new JPasswordField(); // 👈 Add password field
        passwordField.setFont(new Font("Jetbrains Mono", Font.PLAIN, 14)); // Font size for better cursor
    
        JButton loginBtn = new RoundedButton("Login", 40);
        loginBtn.setBackground(new Color(100,149,237));
        loginBtn.setForeground(Color.WHITE);
        loginBtn.setFocusPainted(false);
        loginBtn.setFont(new Font("Jetbrains Mono", Font.BOLD, 14));
        loginBtn.setUI(new javax.swing.plaf.basic.BasicButtonUI());
        loginBtn.setPreferredSize(new Dimension(200, 40));
    
        loginBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                loginBtn.setBackground(new Color(65,105,225));
            }
    
            public void mouseExited(java.awt.event.MouseEvent evt) {
                loginBtn.setBackground(new Color(100,149,237));
            }
        });
    
        panel.add(title);
        panel.add(nameField);
        panel.add(passwordField); // 👈 Add password field to UI
        panel.add(loginBtn);
    
        loginBtn.addActionListener(e -> {
            String name = nameField.getText().trim();
            String password = new String(passwordField.getPassword());
    
            if (!name.isEmpty() && !password.isEmpty()) {
                UserProfile user = users.get(name);
    
                if (user == null) {
                    // First-time login: set password as default
                    currentUser = name;
                    UserProfile newUser = new UserProfile();
                    newUser.password = password;
                    users.put(name, newUser);
                    showMainApp();  
                } else {
                    // Existing user: validate password
                    if (user.password.equals(password)) {
                        currentUser = name;
                        showMainApp();
                    } else {
                        JOptionPane.showMessageDialog(frame, "❌ Incorrect password!", "Login Failed", JOptionPane.ERROR_MESSAGE);
                    }
                }
            } else {
                JOptionPane.showMessageDialog(frame, "⚠️ Please enter both username and password.", "Missing Fields", JOptionPane.WARNING_MESSAGE);
            }
        });
    
        frame.setContentPane(panel);
        frame.revalidate();
    }
    
    private void showMainApp(){

    }
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new main());
    }
    static class UserProfile {
        int xp = 0;
        int coins = 0;
        int level = 1;
        boolean xpBoostActive = false;
        String avatar = "";
        String password = "";
    }
    static class RoundedButton extends JButton{
        private final int radius;
        public RoundedButton(String text, int radius){
            super(text);
            this.radius = radius;
            setOpaque(false);
            setContentAreaFilled(false);
            setFocusPainted(false);
            setBorderPainted(false);
        }

        @Override
        protected void paintComponent(Graphics g){
            Graphics2D g2 = (Graphics2D)g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);

            //Button Background
            g2.setColor(getBackground());
            g2.fillRoundRect(0,0,getWidth(),getHeight(),radius,radius);
            super.paintComponent(g2);
            g2.dispose();
        }
        @Override
        protected void paintBorder(Graphics g){
            Graphics2D g2 = (Graphics2D)g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(getForeground());
            g2.drawRoundRect(0,0,getWidth()-1,getHeight()-1,radius,radius);
            g2.dispose();
        }
    }
}
 