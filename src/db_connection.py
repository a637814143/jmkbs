"""数据库连接与配置工具。"""

from __future__ import annotations

import os
from dataclasses import asdict, dataclass, fields, replace
from typing import Any, Dict, Optional, Type

import pymysql
from pymysql.connections import Connection

_ENV_MAP = {
    "host": "JMK_DB_HOST",
    "port": "JMK_DB_PORT",
    "database": "JMK_DB_NAME",
    "user": "JMK_DB_USER",
    "password": "JMK_DB_PASS",
}


@dataclass(frozen=True)
class DatabaseSettings:
    """保存数据库连接设置的不可变对象。"""

    host: str = "localhost"
    port: int = 3306
    database: str = "jmk_movie"
    user: str = "root"
    password: str = "123456"

    @classmethod
    def from_env(cls, **overrides: Any) -> "DatabaseSettings":
        """根据环境变量和传入的覆盖值创建设置对象。"""

        base = cls()
        env_values: Dict[str, Any] = {}
        for field, env_name in _ENV_MAP.items():
            value = os.getenv(env_name)
            if value is None:
                continue
            if field == "port":
                value = _ensure_port(value)
            env_values[field] = value
        return base.with_overrides(**env_values, **overrides)

    def with_overrides(self, **overrides: Any) -> "DatabaseSettings":
        """返回应用覆盖值后的新设置对象。"""

        prepared: Dict[str, Any] = {}
        for key, value in overrides.items():
            if key not in _SETTINGS_KEYS or value is None:
                continue
            if key == "port":
                prepared[key] = _ensure_port(value)
            else:
                prepared[key] = value
        if not prepared:
            return self
        return replace(self, **prepared)

    def as_dict(self) -> Dict[str, Any]:
        return asdict(self)


def _ensure_port(value: Any) -> int:
    if isinstance(value, int):
        return value
    try:
        return int(str(value))
    except (TypeError, ValueError) as exc:  # pragma: no cover - 防御性处理
        raise ValueError(f"无效的端口值: {value!r}") from exc


def load_settings(**overrides: Any) -> DatabaseSettings:
    """读取环境变量并应用覆盖值，生成最终设置。"""

    return DatabaseSettings.from_env(**overrides)


def create_connection(
    *,
    settings: Optional[DatabaseSettings] = None,
    autocommit: bool = True,
    cursorclass: Type[pymysql.cursors.Cursor] = pymysql.cursors.DictCursor,
    charset: str = "utf8mb4",
    connect_timeout: int = 10,
    **overrides: Any,
) -> Connection:
    """创建并返回一个 PyMySQL 连接。"""

    if settings is None:
        settings = load_settings(**overrides)
    else:
        settings = settings.with_overrides(**overrides)

    return pymysql.connect(
        host=settings.host,
        port=settings.port,
        db=settings.database,
        user=settings.user,
        password=settings.password,
        autocommit=autocommit,
        cursorclass=cursorclass,
        charset=charset,
        connect_timeout=connect_timeout,
    )


_SETTINGS_FIELDS = fields(DatabaseSettings)
_SETTINGS_KEYS = {field.name for field in _SETTINGS_FIELDS}

__all__ = [
    "DatabaseSettings",
    "create_connection",
    "load_settings",
]
