"""Helpers for connecting to the project's MySQL database.

The connection helper centralises the configuration that the rest of the
application should use when opening a database session.  By default, it reads
settings from environment variables so secrets do not have to be committed to
source control, but sensible defaults are provided for local development.

Example
-------
>>> from src.db_connection import create_connection
>>> connection = create_connection()
>>> with connection.cursor() as cursor:
...     cursor.execute("SELECT 1")
...     cursor.fetchone()
{'1': 1}
"""

from __future__ import annotations

import os
from typing import Any, Dict, Optional

import pymysql
from pymysql.connections import Connection

DEFAULT_DATABASE = "jmk_movie"
DEFAULT_USER = "root"
DEFAULT_PASSWORD = "123456"
DEFAULT_HOST = "localhost"
DEFAULT_PORT = 3306


def _coerce_port(value: Optional[Any], *, default: int) -> int:
    """Convert a port value to :class:`int`, falling back to ``default``."""
    if value is None:
        return default
    if isinstance(value, int):
        return value
    try:
        return int(str(value))
    except (TypeError, ValueError) as exc:  # pragma: no cover - defensive
        raise ValueError(f"Invalid port value: {value!r}") from exc


def resolve_connection_settings(
    *,
    host: Optional[str] = None,
    port: Optional[int] = None,
    database: Optional[str] = None,
    user: Optional[str] = None,
    password: Optional[str] = None,
) -> Dict[str, Any]:
    """Resolve connection settings using parameters and environment variables.

    Parameters take precedence over environment variables, which in turn fall
    back to hard-coded defaults that match the values provided in the task
    description.
    """

    resolved_host = host or os.getenv("DB_HOST", DEFAULT_HOST)
    resolved_port = _coerce_port(port or os.getenv("DB_PORT"), default=DEFAULT_PORT)
    resolved_database = database or os.getenv("DB_NAME", DEFAULT_DATABASE)
    resolved_user = user or os.getenv("DB_USER", DEFAULT_USER)
    resolved_password = password or os.getenv("DB_PASSWORD", DEFAULT_PASSWORD)

    return {
        "host": resolved_host,
        "port": resolved_port,
        "database": resolved_database,
        "user": resolved_user,
        "password": resolved_password,
    }


def create_connection(
    *,
    host: Optional[str] = None,
    port: Optional[int] = None,
    database: Optional[str] = None,
    user: Optional[str] = None,
    password: Optional[str] = None,
    charset: str = "utf8mb4",
    cursorclass: Any = pymysql.cursors.DictCursor,
    autocommit: bool = True,
    connect_timeout: int = 10,
) -> Connection:
    """Create and return a new MySQL connection.

    Parameters can be supplied to override the defaults.  When omitted, the
    function will use environment variables (``DB_HOST``, ``DB_PORT``,
    ``DB_NAME``, ``DB_USER``, ``DB_PASSWORD``) with the task's credentials as a
    final fallback.  The returned connection is configured with autocommit
    enabled by default, making it convenient for simple scripts.
    """

    settings = resolve_connection_settings(
        host=host,
        port=port,
        database=database,
        user=user,
        password=password,
    )

    connection = pymysql.connect(
        host=settings["host"],
        port=settings["port"],
        db=settings["database"],
        user=settings["user"],
        password=settings["password"],
        charset=charset,
        cursorclass=cursorclass,
        autocommit=autocommit,
        connect_timeout=connect_timeout,
    )

    return connection


__all__ = [
    "create_connection",
    "resolve_connection_settings",
]
