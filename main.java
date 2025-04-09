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
    private void showLoginScreen() {
        JPanel panel = new JPanel(new GridLayout(6, 1, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(30, 40, 30, 40));
        panel.setBackground(new Color(240, 248, 255));
    
        JLabel title = new JLabel("Welcome to FIT-Quest!", JLabel.CENTER);
        title.setFont(new Font("JetBrains Mono", Font.BOLD, 20));
    
        JLabel usernameLabel = new JLabel("👤 Username:");
        usernameLabel.setFont(new Font("JetBrains Mono", Font.PLAIN, 14));
    
        JTextField nameField = new JTextField();
        nameField.setFont(new Font("JetBrains Mono", Font.PLAIN, 14));
    
        JLabel passwordLabel = new JLabel("🔒 Password:");
        passwordLabel.setFont(new Font("JetBrains Mono", Font.PLAIN, 14));
    
        JPasswordField passwordField = new JPasswordField();
        passwordField.setFont(new Font("JetBrains Mono", Font.PLAIN, 14));
    
        RoundedButton loginBtn = new RoundedButton("Login", 40);
        loginBtn.setBackground(new Color(100, 149, 237));
        loginBtn.setForeground(Color.WHITE);
        loginBtn.setFocusPainted(false);
        loginBtn.setFont(new Font("JetBrains Mono", Font.BOLD, 14));
        loginBtn.setUI(new javax.swing.plaf.basic.BasicButtonUI());
        loginBtn.setPreferredSize(new Dimension(200, 40));
        loginBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
    
        loginBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                loginBtn.setBackground(new Color(65, 105, 225));
            }
    
            public void mouseExited(java.awt.event.MouseEvent evt) {
                loginBtn.setBackground(new Color(100, 149, 237));
            }
        });
    
        // Add components to panel
        panel.add(title);
        panel.add(usernameLabel);
        panel.add(nameField);
        panel.add(passwordLabel);
        panel.add(passwordField);
        panel.add(loginBtn);
    
        // Login logic
        loginBtn.addActionListener(e -> {
            String name = nameField.getText().trim();
            String password = new String(passwordField.getPassword()).trim();
    
            if (!name.isEmpty() && !password.isEmpty()) {
                if (users.containsKey(name)) {
                    if (users.get(name).password.equals(password)) {
                        currentUser = name;
                        showMainApp();
                    } else {
                        JOptionPane.showMessageDialog(frame, "❌ Incorrect password!", "Login Failed", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    // First time user: register
                    UserProfile newUser = new UserProfile();
                    newUser.password = password;
                    users.put(name, newUser);
                    currentUser = name;
                    JOptionPane.showMessageDialog(frame, "✅ Welcome, new user! Your profile has been created.");
                    showMainApp();
                }
            } else {
                JOptionPane.showMessageDialog(frame, "Please enter both username and password.");
            }
        });
    
        frame.setContentPane(panel);
        frame.revalidate();
    }
    
    private void showMainApp() {
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(240, 248, 255));
    
        JLabel welcomeLabel = new JLabel("Welcome back, " + currentUser + " 👋", JLabel.CENTER);
        welcomeLabel.setFont(new Font("Segoe UI", Font.BOLD, 22));
        welcomeLabel.setForeground(new Color(25, 25, 112));
        welcomeLabel.setBorder(BorderFactory.createEmptyBorder(20, 10, 10, 10));
    
        JPanel homeCard = new JPanel();
        homeCard.setLayout(new GridLayout(2, 2, 20, 20));
        homeCard.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
        homeCard.setBackground(new Color(250, 250, 255));
    
        String[] btnNames = {"🏋️ Workout", "🥗 Nutrition", "🏪 Store", "📊 Progress"};
        Color[] colors = {
            new Color(135, 206, 250),
            new Color(152, 251, 152),
            new Color(255, 182, 193),
            new Color(255, 215, 0)
        };
        String[] cardNames = {"Workout", "Nutrition", "Store", "Progress"};
    
        CardLayout cardLayout = new CardLayout();
        JPanel contentPanel = new JPanel(cardLayout);
    
        for (int i = 0; i < btnNames.length; i++) {
            RoundedButton sectionBtn = new RoundedButton(btnNames[i], 30);
            sectionBtn.setFont(new Font("JetBrains Mono", Font.BOLD, 14));
            sectionBtn.setBackground(colors[i]);
            sectionBtn.setForeground(Color.WHITE);
            sectionBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
    
            final String section = cardNames[i];
            sectionBtn.addActionListener(e -> cardLayout.show(contentPanel, section));
    
            homeCard.add(sectionBtn);
        }
    
        // Add the individual section panels
        contentPanel.add(createWorkoutPanel(contentPanel,cardLayout),"Workout");
        contentPanel.add(sectionPanel("🍎 Your Personalized Meals will appear here!", contentPanel, cardLayout), "Nutrition");
    
        // Store Panel with Open Store Button + Back
        JPanel storePanel = sectionPanel("", contentPanel, cardLayout);
        JButton openStoreBtn = new RoundedButton("🛍️ Open Store", 30);
        openStoreBtn.setFont(new Font("Segoe UI Emoji", Font.BOLD, 13));
        openStoreBtn.addActionListener(e -> openStore());
        storePanel.add(openStoreBtn, BorderLayout.CENTER);
        contentPanel.add(storePanel, "Store");
    
        contentPanel.add(sectionPanel("📈 Progress Tracking coming soon!", contentPanel, cardLayout), "Progress");
    
        // Home Wrapper
        JPanel homeWrapper = new JPanel(new BorderLayout());
        homeWrapper.setBackground(new Color(240, 248, 255));
        homeWrapper.add(welcomeLabel, BorderLayout.NORTH);
        homeWrapper.add(homeCard, BorderLayout.CENTER);
    
        contentPanel.add(homeWrapper, "Home");
        cardLayout.show(contentPanel, "Home");
    
        frame.setContentPane(contentPanel);
        frame.revalidate();
        frame.repaint();
    }
    
    private JPanel createWorkoutPanel(JPanel contentPanel, CardLayout cardLayout) {
        JPanel workoutPanel = new JPanel(new BorderLayout());
        workoutPanel.setBackground(new Color(255, 255, 240));
    
        JPanel formPanel = new JPanel(new GridLayout(4, 1, 10, 10));
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));
        formPanel.setBackground(new Color(255, 255, 240));
    
        JTextField ageField = new JTextField();
        ageField.setBorder(BorderFactory.createTitledBorder("Enter Age"));
    
        JTextField heightField = new JTextField();
        heightField.setBorder(BorderFactory.createTitledBorder("Enter Height (cm)"));
    
        JTextField weightField = new JTextField();
        weightField.setBorder(BorderFactory.createTitledBorder("Enter Weight (kg)"));
    
        JButton calcBtn = new RoundedButton("💡 Get Tips", 30);
        calcBtn.setBackground(new Color(60, 179, 113));
        calcBtn.setForeground(Color.WHITE);
    
        calcBtn.addActionListener(e -> {
            try {
                int age = Integer.parseInt(ageField.getText().trim());
                double height = Double.parseDouble(heightField.getText().trim());
                double weight = Double.parseDouble(weightField.getText().trim());
                double bmi = weight / Math.pow(height / 100, 2);
                int suggestedCalories = (int) (22 * Math.pow(height / 100, 2) * 30);
    
                String tips = "";
    
                if (age < 18) {
                    tips = "You're young! Focus on bodyweight exercises like pushups, squats, and develop healthy eating habits.";
                } else if (age <= 40) {
                    if (bmi < 18.5) {
                        tips = "You're underweight. Strength training + calorie-rich balanced meals can help.";
                    } else if (bmi < 24.9) {
                        tips = "Great! You're in a healthy range. Maintain with a mix of cardio and strength.";
                    } else {
                        tips = "Overweight. Focus on cardio, resistance training, and a calorie-deficit diet.";
                    }
                } else {
                    tips = "Prioritize joint-friendly exercises like walking, yoga, or swimming.\nConsult a doctor before intense routines.";
                }
    
                JOptionPane.showMessageDialog(frame,
                    "✅ Age: " + age + "\n" +
                    "📏 Height: " + height + " cm\n" +
                    "⚖️ Weight: " + weight + " kg\n" +
                    "🧮 BMI: " + String.format("%.1f", bmi) + "\n" +
                    "🔥 Suggested Calories: " + suggestedCalories + " kcal/day\n\n" +
                    tips,
                    "Workout Recommendation", JOptionPane.INFORMATION_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(frame, "Please enter valid numbers for all fields!", "Input Error", JOptionPane.ERROR_MESSAGE);
            }
        });
    
        formPanel.add(ageField);
        formPanel.add(heightField);
        formPanel.add(weightField);
        formPanel.add(calcBtn);
    
        JButton backBtn = new RoundedButton("⬅ Back", 25);
        backBtn.setBackground(new Color(220, 20, 60));
        backBtn.setForeground(Color.WHITE);
        backBtn.addActionListener(e -> cardLayout.show(contentPanel, "Home"));
    
        workoutPanel.add(formPanel, BorderLayout.CENTER);
        workoutPanel.add(backBtn, BorderLayout.SOUTH);
    
        return workoutPanel;
    }

    private void openStore(){

    }

    private JPanel sectionPanel(String message, JPanel parentPanel, CardLayout layout) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(255, 255, 255));
    
        JLabel label = new JLabel(message, JLabel.CENTER);
        label.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 16));
        panel.add(label, BorderLayout.CENTER);
    
        // 🏠 Back Button
        RoundedButton backBtn = new RoundedButton("⬅ Back to Home", 30);
        backBtn.setFont(new Font("JetBrains Mono", Font.PLAIN, 12));
        backBtn.setBackground(new Color(100, 149, 237));
        backBtn.setForeground(Color.WHITE);
        backBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        backBtn.addActionListener(e -> layout.show(parentPanel, "Home"));
    
        JPanel south = new JPanel();
        south.setBackground(new Color(255, 255, 255));
        south.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        south.add(backBtn);
    
        panel.add(south, BorderLayout.SOUTH);
    
        return panel;
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
