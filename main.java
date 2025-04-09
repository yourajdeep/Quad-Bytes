import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.List;

import javax.swing.*;
import org.json.*;

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
        contentPanel.add(workoutPanel(contentPanel, cardLayout), "Workout");
        contentPanel.add(nutritionPanel(contentPanel, cardLayout), "Nutrition");
    
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
    
    private JPanel workoutPanel(JPanel parent, CardLayout layout) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(255, 255, 240));
    
        JLabel title = new JLabel("🏋️ Your Personalized Workout Trainer", JLabel.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 20));
        title.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
    
        JTextArea tipsArea = new JTextArea();
        tipsArea.setLineWrap(true);
        tipsArea.setWrapStyleWord(true);
        tipsArea.setEditable(false);
        tipsArea.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        tipsArea.setMargin(new Insets(10, 10, 10, 10));
    
        JScrollPane scrollPane = new JScrollPane(tipsArea);
        scrollPane.setPreferredSize(new Dimension(400, 300));
        scrollPane.setBorder(BorderFactory.createTitledBorder("💬 Chat with Trainer"));
    
        JTextField inputField = new JTextField();
        inputField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        inputField.setPreferredSize(new Dimension(200, 30));
    
        JButton sendBtn = new RoundedButton("📩 Send", 20);
        sendBtn.setFont(new Font("Segoe UI Emoji", Font.BOLD, 13));
        sendBtn.addActionListener(e -> {
            String userMessage = inputField.getText().trim();
            if (!userMessage.isEmpty()) {
                tipsArea.append("🧍 You: " + userMessage + "\n");
                askChatbot(userMessage, tipsArea);
                inputField.setText("");
            }
        });
    
        inputField.addActionListener(e -> sendBtn.doClick()); // Press Enter to send
    
        JPanel inputPanel = new JPanel(new BorderLayout(10, 10));
        inputPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        inputPanel.add(inputField, BorderLayout.CENTER);
        inputPanel.add(sendBtn, BorderLayout.EAST);
    
        JButton backBtn = new RoundedButton("🔙 Back", 20);
        backBtn.setFont(new Font("Segoe UI Emoji", Font.BOLD, 13));
        backBtn.addActionListener(e -> layout.show(parent, "Home"));
    
        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.add(inputPanel, BorderLayout.CENTER);
        bottomPanel.add(backBtn, BorderLayout.SOUTH);
    
        panel.add(title, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(bottomPanel, BorderLayout.SOUTH);
    
        return panel;
    }
    List<Map<String, String>> chatHistory = new ArrayList<>();

private void askChatbot(String userMessage, JTextArea chatArea) {
    chatArea.append("🧠 You: " + userMessage + "\n");
    chatArea.append("🤖 Asking trainer for a tip...\n");

    // Add user message to chat history
    Map<String, String> userMap = new HashMap<>();
    userMap.put("role", "USER");
    userMap.put("message", userMessage);
    chatHistory.add(userMap);

    SwingWorker<Void, Void> worker = new SwingWorker<>() {
        @Override
        protected Void doInBackground() {
            try {
                URL url = new URL("https://api.cohere.ai/v1/chat");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Authorization", "Bearer rn1Yd43hXdJC39PKI6pdbxCXJrlUv3FMzwV60aQk");
                conn.setRequestProperty("Content-Type", "application/json");
                conn.setDoOutput(true);

                JSONObject body = new JSONObject();
                body.put("model", "command-r-plus");
                body.put("message", userMessage);

                // Build chat history array
                JSONArray historyArray = new JSONArray();
                for (Map<String, String> entry : chatHistory) {
                    JSONObject messageObj = new JSONObject();
                    messageObj.put("role", entry.get("role"));
                    messageObj.put("message", entry.get("message"));
                    historyArray.put(messageObj);
                }
                body.put("chat_history", historyArray);

                try (OutputStream os = conn.getOutputStream()) {
                    os.write(body.toString().getBytes());
                }

                BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = in.readLine()) != null) {
                    response.append(line);
                }
                in.close();

                JSONObject json = new JSONObject(response.toString());
                String botReply = json.getString("text");

                // Add bot reply to chat history
                Map<String, String> botMap = new HashMap<>();
                botMap.put("role", "CHATBOT");
                botMap.put("message", botReply);
                chatHistory.add(botMap);

                SwingUtilities.invokeLater(() -> 
                    chatArea.append("💪 Trainer: " + botReply.trim() + "\n\n")
                );

            } catch (Exception e) {
                SwingUtilities.invokeLater(() -> 
                    chatArea.append("⚠️ Error: " + e.getMessage() + "\n\n")
                );
            }
            return null;
        }
    };
    worker.execute();
}
    
     
    private void fetchNutritionInfo(String query, JTextArea resultArea) {
    SwingWorker<Void, Void> worker = new SwingWorker<>() {
        protected Void doInBackground() {
            try {
                URL url = new URL("https://trackapi.nutritionix.com/v2/natural/nutrients");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("POST");
                conn.setRequestProperty("x-app-id", "ea15d219");
                conn.setRequestProperty("x-app-key", "9ba208fd76c671029948a9600a53091f");
                conn.setRequestProperty("Content-Type", "application/json");
                conn.setDoOutput(true);

                String body = "{\"query\": \"" + query + "\"}";
                try (OutputStream os = conn.getOutputStream()) {
                    os.write(body.getBytes());
                }
                catch (Exception e) {
                    e.printStackTrace(); // <-- This shows the real error in console
                    SwingUtilities.invokeLater(() -> resultArea.setText("⚠️ Error: " + e.getMessage()));
                }

                BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                StringBuilder content = new StringBuilder();
                String line;
                while ((line = in.readLine()) != null) content.append(line);
                in.close();

                JSONObject json = new JSONObject(content.toString());
                JSONArray foods = json.getJSONArray("foods");
                JSONObject food = foods.getJSONObject(0);

                int calories = food.getInt("nf_calories");
                double protein = food.getDouble("nf_protein");
                double fat = food.getDouble("nf_total_fat");

                String result = "Calories: " + calories + " \n"
                              + "Protein: " + protein + "g\n"
                              + "Fat: " + fat + "g\n\n"
                              + "🏃 Run: " + (calories / 10) + " mins\n"
                              + "💪 Workout: " + (calories / 7) + " mins";

                SwingUtilities.invokeLater(() -> resultArea.setText(result));

            } catch (Exception e) {
                SwingUtilities.invokeLater(() -> resultArea.setText("⚠️ Error: " + e.getMessage()));
            }
            return null;
        }
    };
    worker.execute();
}
private JPanel nutritionPanel(JPanel contentPanel, CardLayout cardLayout) {
    JPanel panel = new JPanel(new BorderLayout());
    panel.setBackground(new Color(255, 255, 240));

    JLabel heading = new JLabel("🍎 Personalized Nutrition", JLabel.CENTER);
    heading.setFont(new Font("Segoe UI", Font.BOLD, 20));
    heading.setBorder(BorderFactory.createEmptyBorder(20, 10, 10, 10));

    JTextField inputField = new JTextField();
    inputField.setFont(new Font("JetBrains Mono", Font.PLAIN, 14));
    inputField.setBorder(BorderFactory.createTitledBorder("Enter a food item"));

    JTextArea resultArea = new JTextArea(8, 40);
    resultArea.setFont(new Font("JetBrains Mono", Font.PLAIN, 13));
    resultArea.setEditable(false);
    resultArea.setLineWrap(true);
    resultArea.setWrapStyleWord(true);
    JScrollPane scrollPane = new JScrollPane(resultArea);

    JButton fetchBtn = new RoundedButton("🍽️ Get Nutrition", 30);
    fetchBtn.setFont(new Font("Segoe UI", Font.BOLD, 14));
    fetchBtn.setBackground(new Color(144, 238, 144));
    fetchBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
    fetchBtn.addActionListener(e -> {
        String query = inputField.getText().trim();
        if (!query.isEmpty()) {
            resultArea.setText("⏳ Fetching nutrition data...");
            fetchNutritionInfo(query, resultArea);
        } else {
            resultArea.setText("⚠️ Please enter a food item.");
        }
    });

    JButton backBtn = new RoundedButton("⬅️ Back", 30);
    backBtn.setBackground(Color.LIGHT_GRAY);
    backBtn.addActionListener(e -> cardLayout.show(contentPanel, "Home"));

    JPanel centerPanel = new JPanel();
    centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
    centerPanel.setBackground(new Color(255, 255, 240));
    centerPanel.add(inputField);
    centerPanel.add(Box.createRigidArea(new Dimension(0, 10)));
    centerPanel.add(fetchBtn);
    centerPanel.add(Box.createRigidArea(new Dimension(0, 10)));
    centerPanel.add(scrollPane);

    JPanel southPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
    southPanel.setBackground(new Color(255, 255, 240));
    southPanel.add(backBtn);

    panel.add(heading, BorderLayout.NORTH);
    panel.add(centerPanel, BorderLayout.CENTER);
    panel.add(southPanel, BorderLayout.SOUTH);

    return panel;
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
