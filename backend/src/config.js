/** Đọc cấu hình từ biến môi trường, có giá trị mặc định để chạy local. */
export const config = {
  port: Number(process.env.PORT ?? 3000),
  corsOrigin: process.env.CORS_ORIGIN ?? '*',
  tokenTtlHours: Number(process.env.TOKEN_TTL_HOURS ?? 72),
};
