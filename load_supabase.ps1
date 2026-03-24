$pairs = (Get-Content .env | Where-Object { $_ -and ($_ -notmatch '^\s*#') }) -join "`n"
$cfg = ConvertFrom-StringData -StringData $pairs

if (-not $cfg.SUPABASE_DB_URL -or -not $cfg.SUPABASE_DB_USER -or -not $cfg.SUPABASE_DB_PASSWORD) {
    throw "Faltan variables SUPABASE_DB_URL/USER/PASSWORD en .env"
}

$u = $cfg.SUPABASE_DB_URL -replace '^jdbc:postgresql://', ''
$hpdb = ($u -split '\?')[0]
$ssl = 'require'
if ($u -match 'sslmode=([^&]+)') { $ssl = $Matches[1] }

$hp, $db = $hpdb -split '/', 2
$dbHost, $port = $hp -split ':', 2
if (-not $port) { $port = '5432' }
if (-not $db) { $db = 'postgres' }

$user = $cfg.SUPABASE_DB_USER
$pass = $cfg.SUPABASE_DB_PASSWORD

Write-Host "Aplicando schema en Supabase..."
docker run --rm -v "${PWD}:/work" -w /work -e PGPASSWORD="$pass" -e PGSSLMODE="$ssl" postgres:15-alpine psql -v ON_ERROR_STOP=1 -h "$dbHost" -p "$port" -U "$user" -d "$db" -f db/init/01_schema.sql
if ($LASTEXITCODE -ne 0) { throw "Fallo al aplicar schema" }

Write-Host "Aplicando seed de datos..."
docker run --rm -v "${PWD}:/work" -w /work -e PGPASSWORD="$pass" -e PGSSLMODE="$ssl" postgres:15-alpine psql -v ON_ERROR_STOP=1 -h "$dbHost" -p "$port" -U "$user" -d "$db" -f db/seed_data.sql
if ($LASTEXITCODE -ne 0) { throw "Fallo al aplicar seed" }

Write-Host "Verificando conteos..."
docker run --rm -e PGPASSWORD="$pass" -e PGSSLMODE="$ssl" postgres:15-alpine psql -h "$dbHost" -p "$port" -U "$user" -d "$db" -c "select count(*) as clientes from clientes; select count(*) as cuentas from cuentas; select count(*) as transacciones from transacciones;"
