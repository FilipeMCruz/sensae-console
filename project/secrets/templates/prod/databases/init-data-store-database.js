db.createUser({
  user: "user",
  pwd: "$SENSAE_DATA_STORE_USER_PASSWORD",
  roles: [{ role: "readWrite", db: "data" }],
});
