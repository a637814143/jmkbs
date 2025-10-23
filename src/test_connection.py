"""命令行工具：验证与 jmk_movie 数据库的连接。"""

from __future__ import annotations

import argparse
import sys

from pymysql.err import OperationalError

from .db_connection import DatabaseSettings, create_connection


def parse_args() -> argparse.Namespace:
    parser = argparse.ArgumentParser(description=__doc__)
    parser.add_argument("--host", help="数据库主机")
    parser.add_argument("--port", type=int, help="数据库端口")
    parser.add_argument("--database", help="数据库名称")
    parser.add_argument("--user", help="数据库用户名")
    parser.add_argument("--password", help="数据库密码")
    return parser.parse_args()


def main() -> int:
    args = parse_args()
    overrides = {
        key: getattr(args, key)
        for key in ["host", "port", "database", "user", "password"]
        if getattr(args, key) is not None
    }

    settings = DatabaseSettings.from_env(**overrides)
    print("尝试连接，当前设置：")
    for key, value in settings.as_dict().items():
        if key == "password":
            display = "***" if value else ""
        else:
            display = value
        print(f"  {key}: {display}")

    try:
        connection = create_connection(settings=settings)
    except OperationalError as exc:
        print("连接失败：", exc)
        return 1

    with connection:
        with connection.cursor() as cursor:
            cursor.execute("SELECT 1")
            row = cursor.fetchone()
    print("连接成功，SELECT 1 返回：", row)
    return 0


if __name__ == "__main__":
    sys.exit(main())
