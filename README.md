# jmk_movie 数据库连接工具

本仓库提供了一套轻量级的 Python 工具，用于连接到 MySQL 数据库
`jmk_movie`。默认凭据如下：

- **主机**：`localhost`
- **端口**：`3306`
- **数据库**：`jmk_movie`
- **用户名**：`root`
- **密码**：`123456`

工具通过 `DatabaseSettings` 数据类统一管理连接信息，可从环境变量读取，
也可以在命令行或代码里覆盖，方便在不同环境下使用。

## 环境准备

1. 安装 Python 3.9 及以上版本。
2. 安装依赖：

   ```bash
   pip install -r requirements.txt
   ```

## 在代码中创建连接

```python
from src import create_connection, DatabaseSettings

# 使用默认设置（可自动读取环境变量覆盖）
connection = create_connection()

# 或者手动指定参数
custom_settings = DatabaseSettings(host="db.internal", password="s3cret")
connection = create_connection(settings=custom_settings)
```

`create_connection` 默认开启 `autocommit` 并使用 `DictCursor`，适合脚本类
场景。如果需要微调，可以传入 `autocommit=False`、`cursorclass` 等参数。

## 命令行测试连接

仓库提供了一个简单的 CLI 工具，用来验证当前凭据是否能成功连接：

```bash
python -m src.test_connection
```

常用覆盖方式：

```bash
# 使用环境变量
export JMK_DB_HOST=database.internal
export JMK_DB_PASSWORD=better_password
python -m src.test_connection

# 或在命令行直接传参
python -m src.test_connection --host database.internal --password better_password
```

运行成功后会输出 `SELECT 1` 的查询结果；失败时会给出错误信息，方便排查。

## 环境变量

| 变量名          | 说明                 |
|-----------------|----------------------|
| `JMK_DB_HOST`   | 数据库主机地址       |
| `JMK_DB_PORT`   | 数据库端口（整数）   |
| `JMK_DB_NAME`   | 数据库名称           |
| `JMK_DB_USER`   | 数据库用户名         |
| `JMK_DB_PASS`   | 数据库密码           |

如果未设置对应变量，则会回退到表格顶部列出的默认值。
