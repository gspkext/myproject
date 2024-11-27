# Set base URL
$BASE_URL = "http://localhost:8081"  # 更新为正确的端口

Write-Host "Starting tests..." -ForegroundColor Yellow

# Define Test-Api function
function Test-Api {
    param (
        [string]$Method,
        [string]$Endpoint,
        [string]$Data,
        [string]$Description
    )

    Write-Host "`nTesting $Description..." -ForegroundColor Yellow
    try {
        if ($Method -eq "POST") {
            $response = Invoke-RestMethod -Uri "$BASE_URL$Endpoint" -Method $Method -Body $Data -ContentType "application/json"
        } else {
            $response = Invoke-RestMethod -Uri "$BASE_URL$Endpoint" -Method $Method
        }
        Write-Host "$Description successful" -ForegroundColor Green
        if ($response) {
            Write-Host "Response:" -ForegroundColor Green
            $response | ConvertTo-Json | Write-Host
        }
        return $true
    } catch {
        Write-Host "$Description failed: $($_.Exception.Message)" -ForegroundColor Red
        Write-Host "Full error details: $($_)" -ForegroundColor Red
        return $false
    }
}

# Test different endpoints
Write-Host "`nTesting available endpoints..." -ForegroundColor Yellow
$endpoints = @(
    "/",
    "/product/list",
    "/chain/list",
    "/actuator/health"
)

foreach ($endpoint in $endpoints) {
    Write-Host "`nTrying endpoint: $BASE_URL$endpoint" -ForegroundColor Yellow
    try {
        $response = Invoke-WebRequest -Uri "$BASE_URL$endpoint" -Method GET
        Write-Host "Success! Status code: $($response.StatusCode)" -ForegroundColor Green
    } catch {
        Write-Host "Failed: $($_.Exception.Message)" -ForegroundColor Red
        if ($_.Exception.Response.StatusCode -eq 500) {
            Write-Host "This might be a database connection issue" -ForegroundColor Yellow
        }
    }
}

# Test add product
$product = @{
    id = "test_001"
    name = "Test Product"
    createTime = (Get-Date).ToString("yyyy-MM-dd HH:mm:ss")
} | ConvertTo-Json

Test-Api -Method "POST" -Endpoint "/product/add" -Data $product -Description "Add Product"

# Test query product list
Test-Api -Method "GET" -Endpoint "/product/list" -Description "Query Product List"

Write-Host "`nTests completed" -ForegroundColor Yellow