// Main Application JavaScript
// API Configuration
const API_BASE = '/api';
const AUTH_CREDENTIALS = btoa('admin:admin123');

// Utility Functions
function getAuthHeaders() {
    return {
        'Authorization': 'Basic ' + AUTH_CREDENTIALS,
        'Content-Type': 'application/json'
    };
}

function showLoading() {
    const loading = document.getElementById('loading');
    if (loading) {
        loading.style.display = 'flex';
    }
}

function hideLoading() {
    const loading = document.getElementById('loading');
    if (loading) {
        loading.style.display = 'none';
    }
}

function showAlert(message, type = 'success', container = '.content') {
    const alertContainer = document.querySelector(container);
    if (!alertContainer) return;

    const alertId = 'alert-' + Date.now();
    const iconMap = {
        'success': 'bi-check-circle-fill',
        'danger': 'bi-exclamation-triangle-fill',
        'warning': 'bi-exclamation-triangle-fill',
        'info': 'bi-info-circle-fill'
    };

    const alertHtml = `
        <div id="${alertId}" class="alert alert-${type} alert-dismissible fade show" role="alert">
            <i class="bi ${iconMap[type]} me-2"></i>
            ${message}
            <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
        </div>
    `;

    alertContainer.insertAdjacentHTML('afterbegin', alertHtml);

    // Auto remove after 5 seconds
    setTimeout(() => {
        const alert = document.getElementById(alertId);
        if (alert) {
            const bsAlert = new bootstrap.Alert(alert);
            bsAlert.close();
        }
    }, 5000);
}

// API Service Class
class ApiService {
    static async get(endpoint) {
        try {
            showLoading();
            const response = await fetch(API_BASE + endpoint, {
                method: 'GET',
                headers: getAuthHeaders()
            });

            if (!response.ok) {
                throw new Error(`HTTP error! status: ${response.status}`);
            }

            const data = await response.json();
            hideLoading();
            return data;
        } catch (error) {
            hideLoading();
            console.error('API GET Error:', error);
            showAlert('Gagal memuat data: ' + error.message, 'danger');
            throw error;
        }
    }

    static async post(endpoint, data) {
        try {
            showLoading();
            const response = await fetch(API_BASE + endpoint, {
                method: 'POST',
                headers: getAuthHeaders(),
                body: JSON.stringify(data)
            });

            if (!response.ok) {
                const errorText = await response.text();
                throw new Error(errorText || `HTTP error! status: ${response.status}`);
            }

            const result = await response.json();
            hideLoading();
            return result;
        } catch (error) {
            hideLoading();
            console.error('API POST Error:', error);
            showAlert('Gagal menyimpan data: ' + error.message, 'danger');
            throw error;
        }
    }

    static async put(endpoint, data) {
        try {
            showLoading();
            const response = await fetch(API_BASE + endpoint, {
                method: 'PUT',
                headers: getAuthHeaders(),
                body: JSON.stringify(data)
            });

            if (!response.ok) {
                const errorText = await response.text();
                throw new Error(errorText || `HTTP error! status: ${response.status}`);
            }

            const result = await response.json();
            hideLoading();
            return result;
        } catch (error) {
            hideLoading();
            console.error('API PUT Error:', error);
            showAlert('Gagal mengupdate data: ' + error.message, 'danger');
            throw error;
        }
    }

    static async delete(endpoint) {
        try {
            showLoading();
            const response = await fetch(API_BASE + endpoint, {
                method: 'DELETE',
                headers: getAuthHeaders()
            });

            if (!response.ok) {
                const errorText = await response.text();
                throw new Error(errorText || `HTTP error! status: ${response.status}`);
            }

            hideLoading();
            return true;
        } catch (error) {
            hideLoading();
            console.error('API DELETE Error:', error);
            showAlert('Gagal menghapus data: ' + error.message, 'danger');
            throw error;
        }
    }

    static async get(endpoint, params) {
        try {
            showLoading();
            const url = new URL(API_BASE + endpoint, window.location.origin);
            Object.keys(params).forEach(key => url.searchParams.append(key, params[key]));

            const response = await fetch(url.toString(), {
                method: 'GET',
                headers: getAuthHeaders()
            });

            if (!response.ok) {
                throw new Error(`HTTP error! status: ${response.status}`);
            }

            const data = await response.json();
            hideLoading();
            return data;
        } catch (error) {
            hideLoading();
            console.error('API GET with params Error:', error);
            showAlert('Gagal memuat data: ' + error.message, 'danger');
            throw error;
        }
    }
}

// Product Management Service
class ProductService {
    static async getAll() {
        return await ApiService.get('/products');
    }

    static async getById(id) {
        return await ApiService.get(`/products/${id}`);
    }

    static async create(product) {
        return await ApiService.post('/products', product);
    }

    static async update(id, product) {
        return await ApiService.put(`/products/${id}`, product);
    }

    static async delete(id) {
        return await ApiService.delete(`/products/${id}`);
    }

    static async search(name) {
        return await ApiService.get(`/products/search?name=${encodeURIComponent(name)}`);
    }

    static async getByPriceRange(min, max) {
        return await ApiService.get(`/products/price-range?minPrice=${min}&maxPrice=${max}`);
    }

    static async getInStock() {
        return await ApiService.get('/products/in-stock');
    }

    static async getLowStock(threshold = 10) {
        return await ApiService.get(`/products/low-stock/${threshold}`);
    }

    static async getOutOfStock() {
        return await ApiService.get('/products/out-of-stock');
    }
}

// Transaction Management Service
class TransactionService {
    static async getAll() {
        return await ApiService.get('/transactions');
    }

    static async getById(id) {
        return await ApiService.get(`/transactions/${id}`);
    }

    static async create(transaction) {
        return await ApiService.post('/transactions', transaction);
    }

    static async createSale(productId, quantity, description) {
        return await ApiService.get('/transactions/sale', {
            productId: productId,
            quantity: quantity,
            description: description
        });
    }

    static async createPurchase(productId, quantity, description) {
        return await ApiService.get('/transactions/purchase', {
            productId: productId,
            quantity: quantity,
            description: description
        });
    }

    static async update(id, transaction) {
        return await ApiService.put(`/transactions/${id}`, transaction);
    }

    static async delete(id) {
        return await ApiService.delete(`/transactions/${id}`);
    }

    static async getByProduct(productId) {
        return await ApiService.get(`/transactions/product/${productId}`);
    }

    static async getByType(type) {
        return await ApiService.get(`/transactions/type/${type}`);
    }

    static async getByDateRange(startDate, endDate) {
        return await ApiService.get('/transactions/date-range', {
            startDate: startDate.toISOString(),
            endDate: endDate.toISOString()
        });
    }

    static async getRecent() {
        return await ApiService.get('/transactions/recent');
    }

    static async getTotalSales() {
        return await ApiService.get('/transactions/stats/total-sales');
    }

    static async getTotalPurchases() {
        return await ApiService.get('/transactions/stats/total-purchases');
    }

    static async getNetRevenue() {
        return await ApiService.get('/transactions/stats/net-revenue');
    }
}

// Statistics Service
class StatisticsService {
    static async getDashboardStats() {
        try {
            const [products, transactions] = await Promise.all([
                ProductService.getAll(),
                TransactionService.getAll()
            ]);

            const inStock = products.filter(p => p.stock > 0).length;
            const lowStock = products.filter(p => p.stock <= 10).length;
            
            const sales = transactions.filter(t => t.transactionType === 'SALE');
            const purchases = transactions.filter(t => t.transactionType === 'PURCHASE');

            const totalSales = sales.reduce((sum, t) => sum + t.totalPrice, 0);
            const totalPurchases = purchases.reduce((sum, t) => sum + t.totalPrice, 0);

            return {
                totalProducts: products.length,
                inStockProducts: inStock,
                lowStockProducts: lowStock,
                outOfStockProducts: products.length - inStock,
                totalTransactions: transactions.length,
                totalSales: totalSales,
                totalPurchases: totalPurchases,
                netRevenue: totalSales - totalPurchases,
                sales: sales.length,
                purchases: purchases.length
            };
        } catch (error) {
            console.error('Error getting dashboard stats:', error);
            throw error;
        }
    }
}

// Utility Functions
function formatCurrency(amount) {
    return new Intl.NumberFormat('id-ID', {
        style: 'currency',
        currency: 'IDR',
        minimumFractionDigits: 0
    }).format(amount);
}

function formatDate(dateString) {
    const date = new Date(dateString);
    return date.toLocaleString('id-ID', {
        day: '2-digit',
        month: '2-digit',
        year: 'numeric',
        hour: '2-digit',
        minute: '2-digit'
    });
}

function formatShortDate(dateString) {
    const date = new Date(dateString);
    return date.toLocaleDateString('id-ID', {
        day: '2-digit',
        month: '2-digit',
        year: 'numeric'
    });
}

function debounce(func, wait) {
    let timeout;
    return function executedFunction(...args) {
        const later = () => {
            clearTimeout(timeout);
            func(...args);
        };
        clearTimeout(timeout);
        timeout = setTimeout(later, wait);
    };
}

// Modal Management
class ModalManager {
    static show(modalId) {
        const modalElement = document.getElementById(modalId);
        if (modalElement) {
            const modal = new bootstrap.Modal(modalElement);
            modal.show();
            return modal;
        }
        return null;
    }

    static hide(modalId) {
        const modalElement = document.getElementById(modalId);
        if (modalElement) {
            const modal = bootstrap.Modal.getInstance(modalElement);
            if (modal) {
                modal.hide();
            }
        }
    }

    static showConfirm(title, message, onConfirm, onCancel = null) {
        const confirmModal = document.createElement('div');
        confirmModal.className = 'modal fade';
        confirmModal.innerHTML = `
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title">${title}</h5>
                        <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
                    </div>
                    <div class="modal-body">
                        ${message}
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Batal</button>
                        <button type="button" class="btn btn-danger" id="confirmBtn">Ya</button>
                    </div>
                </div>
            </div>
        `;

        document.body.appendChild(confirmModal);
        const modal = new bootstrap.Modal(confirmModal);

        document.getElementById('confirmBtn').addEventListener('click', () => {
            if (onConfirm) onConfirm();
            modal.hide();
            document.body.removeChild(confirmModal);
        });

        confirmModal.addEventListener('hidden.bs.modal', () => {
            if (onCancel) onCancel();
            if (document.body.contains(confirmModal)) {
                document.body.removeChild(confirmModal);
            }
        });

        modal.show();
    }
}

// Form Validation
class FormValidator {
    static validateProduct(product) {
        const errors = [];

        if (!product.name || product.name.trim().length === 0) {
            errors.push('Nama produk harus diisi');
        }

        if (!product.price || product.price <= 0) {
            errors.push('Harga harus lebih dari 0');
        }

        if (!product.stock || product.stock < 0) {
            errors.push('Stok tidak boleh negatif');
        }

        if (product.name && product.name.length > 255) {
            errors.push('Nama produk maksimal 255 karakter');
        }

        if (product.price && product.price > 999999999) {
            errors.push('Harga terlalu besar');
        }

        return {
            isValid: errors.length === 0,
            errors: errors
        };
    }

    static validateTransaction(transaction) {
        const errors = [];

        if (!transaction.productId || transaction.productId <= 0) {
            errors.push('Produk harus dipilih');
        }

        if (!transaction.quantity || transaction.quantity <= 0) {
            errors.push('Quantity harus lebih dari 0');
        }

        if (!transaction.transactionType) {
            errors.push('Tipe transaksi harus dipilih');
        }

        return {
            isValid: errors.length === 0,
            errors: errors
        };
    }
}

// Export functionality
class ExportService {
    static toCSV(data, headers) {
        const csvHeaders = headers.join(',');
        const csvRows = data.map(row => 
            headers.map(header => {
                const value = row[header] || '';
                return `"${String(value).replace(/"/g, '""')}"`;
            }).join(',')
        );
        
        return csvHeaders + '\n' + csvRows.join('\n');
    }

    static downloadCSV(csv, filename) {
        const blob = new Blob([csv], { type: 'text/csv;charset=utf-8;' });
        const link = document.createElement('a');
        const url = URL.createObjectURL(blob);
        
        link.setAttribute('href', url);
        link.setAttribute('download', filename);
        link.style.visibility = 'hidden';
        
        document.body.appendChild(link);
        link.click();
        document.body.removeChild(link);
    }

    static toJSON(data, filename) {
        const json = JSON.stringify(data, null, 2);
        const blob = new Blob([json], { type: 'application/json' });
        const link = document.createElement('a');
        const url = URL.createObjectURL(blob);
        
        link.setAttribute('href', url);
        link.setAttribute('download', filename);
        link.style.visibility = 'hidden';
        
        document.body.appendChild(link);
        link.click();
        document.body.removeChild(link);
    }
}

// Initialize on page load
document.addEventListener('DOMContentLoaded', function() {
    console.log('Application initialized');
    
    // Initialize tooltips
    const tooltipTriggerList = [].slice.call(document.querySelectorAll('[data-bs-toggle="tooltip"]'));
    tooltipTriggerList.map(function (tooltipTriggerEl) {
        return new bootstrap.Tooltip(tooltipTriggerEl);
    });
    
    // Initialize popovers
    const popoverTriggerList = [].slice.call(document.querySelectorAll('[data-bs-toggle="popover"]'));
    popoverTriggerList.map(function (popoverTriggerEl) {
        return new bootstrap.Popover(popoverTriggerEl);
    });
    
    // Auto-hide alerts after 5 seconds
    setTimeout(() => {
        const alerts = document.querySelectorAll('.alert');
        alerts.forEach(alert => {
            const bsAlert = new bootstrap.Alert(alert);
            bsAlert.close();
        });
    }, 5000);
});

// Export global functions for use in HTML templates
window.ApiService = ApiService;
window.ProductService = ProductService;
window.TransactionService = TransactionService;
window.StatisticsService = StatisticsService;
window.ModalManager = ModalManager;
window.FormValidator = FormValidator;
window.ExportService = ExportService;
window.showLoading = showLoading;
window.hideLoading = hideLoading;
window.showAlert = showAlert;
window.formatCurrency = formatCurrency;
window.formatDate = formatDate;
window.debounce = debounce;