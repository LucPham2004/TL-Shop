// Firebase configuration
const firebaseConfig = {
    apiKey: "YOUR_API_KEY",
    authDomain: "YOUR_AUTH_DOMAIN",
    projectId: "YOUR_PROJECT_ID",
    storageBucket: "YOUR_STORAGE_BUCKET",
    messagingSenderId: "YOUR_MESSAGING_SENDER_ID",
    appId: "YOUR_APP_ID"
  };

  // Initialize Firebase
  firebase.initializeApp(firebaseConfig);
  const auth = firebase.auth();

  // Email/password login
  document.getElementById('login-form').addEventListener('submit', (e) => {
    e.preventDefault();
    const email = document.getElementById('email').value;
    const password = document.getElementById('password').value;

    auth.signInWithEmailAndPassword(email, password)
      .then(userCredential => {
        console.log('User signed in:', userCredential.user);
      })
      .catch(error => {
        console.error('Error signing in:', error);
      });
  });

  // Google login
  document.getElementById('google-login').addEventListener('click', () => {
    const provider = new firebase.auth.GoogleAuthProvider();
    auth.signInWithPopup(provider)
      .then(result => {
        console.log('User signed in with Google:', result.user);
      })
      .catch(error => {
        console.error('Error signing in with Google:', error);
      });
  });

  // Facebook login
  document.getElementById('facebook-login').addEventListener('click', () => {
    const provider = new firebase.auth.FacebookAuthProvider();
    auth.signInWithPopup(provider)
      .then(result => {
        console.log('User signed in with Facebook:', result.user);
      })
      .catch(error => {
        console.error('Error signing in with Facebook:', error);
      });
  });