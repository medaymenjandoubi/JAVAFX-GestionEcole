/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.esprit.entities;

/**
 *
 * @author 21628
 */
public class SessionManager {
        private static String loggedInUsername;
        private static String loggedInUserRole;

        public static String getLoggedInUsername() {
            return loggedInUsername;
        }

        public static String getLoggedInUserRole() {
            return loggedInUserRole;
        }

        public static void setLoggedInUser(String username, String role) {
            loggedInUsername = username;
            loggedInUserRole = role;
        }

        public static void clearSession() {
            loggedInUsername = null;
            loggedInUserRole = null;
        }
}
