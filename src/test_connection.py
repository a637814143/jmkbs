"""Simple utility to verify that the database credentials work."""

from __future__ import annotations

import argparse
import sys

from pymysql.err import OperationalError

from .db_connection import create_connection, resolve_connection_settings


def parse_args() -> argparse.Namespace:
    parser = argparse.ArgumentParser(description=__doc__)
    parser.add_argument("--host", help="Database host override")
    parser.add_argument("--port", type=int, help="Database port override")
    parser.add_argument("--database", help="Database name override")
    parser.add_argument("--user", help="Database user override")
    parser.add_argument("--password", help="Database password override")
    return parser.parse_args()


def main() -> int:
    args = parse_args()
    overrides = {
        key: value
        for key, value in {
            "host": args.host,
            "port": args.port,
            "database": args.database,
            "user": args.user,
            "password": args.password,
        }.items()
        if value is not None
    }

    settings = resolve_connection_settings(**overrides)
    print("Attempting to connect with settings:")
    print(
        f"  host={settings['host']} port={settings['port']}\n"
        f"  database={settings['database']} user={settings['user']}"
    )

    try:
        connection = create_connection(**overrides)
    except OperationalError as exc:
        print("Connection failed:", exc)
        return 1

    with connection:
        with connection.cursor() as cursor:
            cursor.execute("SELECT 1")
            result = cursor.fetchone()
    print("Connection successful, SELECT 1 returned:", result)
    return 0


if __name__ == "__main__":
    sys.exit(main())
