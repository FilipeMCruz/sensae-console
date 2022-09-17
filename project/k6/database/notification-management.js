import sql from "k6/x/sql";

export function subscribeAnonymous(id) {
  notificationDB.exec(`INSERT INTO public.addressee_config (id, category, sub_category, level, show_ui, send_sms, send_email, send_notification)
  VALUES ('${id}', 'perfTest', 'alarmPerformance', 'critical', true, false, false, true);`);
}

export function clearNotifications() {
  notificationDB.exec("TRUNCATE public.notification CASCADE;");
  notificationDB.exec("TRUNCATE public.read CASCADE;");
  notificationDB.exec("TRUNCATE public.addressee_config CASCADE;");
  notificationDB.close();
}

export function countNotifications() {
  let count = sql.query(notificationDB, "SELECT count(*) FROM public.notification;");
  return count[0].count;
}

const notificationDB = sql.open(
  "postgres",
  `postgres://user:${__ENV.SENSAE_COMMON_DATABASE_PASSWORD}@localhost:5496/notification?sslmode=disable`
);
